/*
 * Copyright 2017 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.rta.files;

import com.trivago.rta.exceptions.CucablePluginException;
import com.trivago.rta.exceptions.filesystem.FileDeletionException;
import com.trivago.rta.exceptions.filesystem.PathCreationException;
import com.trivago.rta.properties.PropertyManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class FileSystemManager {

    private static final String FEATURE_SUFFIX = ".feature";
    private final PropertyManager propertyManager;

    @Inject
    public FileSystemManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    /**
     * Create generated feature and runner dirs if they don't exist and clear their contents.
     */
    public void prepareGeneratedFeatureAndRunnerDirs() throws CucablePluginException {
        createDirIfNotExists(propertyManager.getGeneratedFeatureDirectory());
        removeFilesFromPath(propertyManager.getGeneratedFeatureDirectory(), "feature");

        createDirIfNotExists(propertyManager.getGeneratedRunnerDirectory());
        removeFilesFromPath(propertyManager.getGeneratedRunnerDirectory(), "java");
    }

    /**
     * Returns a list of feature file paths located in the specified source feature directory.
     *
     * @return a list of feature file paths.
     * @throws CucablePluginException see {@link CucablePluginException}
     */
    public List<Path> getFeatureFilePaths() throws CucablePluginException {

        List<Path> featureFilePaths = new ArrayList<>();
        String sourceFeatures = propertyManager.getSourceFeatures();
        File sourceFeaturesFile = new File(sourceFeatures);

        // Check if the property value is a single file or a directory
        if (sourceFeaturesFile.isFile() && sourceFeatures.endsWith(FEATURE_SUFFIX)) {
            featureFilePaths.add(Paths.get(sourceFeatures));
        } else if (sourceFeaturesFile.isDirectory()) {
            try {
                featureFilePaths =
                        Files.walk(Paths.get(sourceFeatures))
                                .filter(Files::isRegularFile)
                                .filter(p -> p.toString().endsWith(FEATURE_SUFFIX))
                                .collect(Collectors.toList());

            } catch (IOException e) {
                throw new CucablePluginException(
                        "Unable to traverse feature files in " + sourceFeatures);
            }
        } else {
            throw new CucablePluginException(
                    sourceFeatures + " is not a feature file or a directory."
            );
        }

        return featureFilePaths;
    }

    /**
     * Creates a directory if it does not exists.
     *
     * @param dirName Name of directory.
     */
    private void createDirIfNotExists(final String dirName) throws PathCreationException {
        File directory = new File(dirName);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new PathCreationException(dirName);
        }
    }

    /**
     * Removes files with the specified extension from the given path.
     *
     * @param path          The path to clear.
     * @param fileExtension The file extension to consider.
     */
    private void removeFilesFromPath(final String path, final String fileExtension)
            throws FileDeletionException {

        File basePath = new File(path);
        File[] files = basePath.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith("." + fileExtension) && !file.delete()) {
                    throw new FileDeletionException(file.getName());
                }
            }
        }
    }
}

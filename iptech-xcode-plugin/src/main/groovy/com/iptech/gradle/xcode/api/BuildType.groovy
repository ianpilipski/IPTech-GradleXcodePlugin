package com.iptech.gradle.xcode.api

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

abstract class BuildType {
    final String name

    @Input abstract Property<String> getConfiguration()
    @Input abstract Property<String> getScheme()
    @Input abstract DirectoryProperty getArchivePath()

    BuildType(String name) {
        this.name = name
    }
}
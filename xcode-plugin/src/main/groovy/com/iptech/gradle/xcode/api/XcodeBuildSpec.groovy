package com.iptech.gradle.xcode.api

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

interface XcodeBuildSpec {
    @InputDirectory @Optional DirectoryProperty getProjectPath()
    @Input @Optional Property<String> getConfiguration()
    @Input @Optional Property<String> getScheme()
    @InputDirectory @Optional DirectoryProperty getArchivePath()
    @InputDirectory @Optional DirectoryProperty getDerivedDataPath()
    @InputFile @Optional RegularFileProperty getExportOptionsPlist()
    @OutputDirectory @Optional DirectoryProperty getExportPath()
    @Input @Optional Property<Boolean> getAutomaticSigning()

    @Input @Optional ListProperty<String> getAdditionalArguments()
}
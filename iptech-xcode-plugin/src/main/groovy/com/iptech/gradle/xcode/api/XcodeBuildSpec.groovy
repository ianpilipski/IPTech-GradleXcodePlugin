package com.iptech.gradle.xcode.api

import javafx.beans.property.ListProperty
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

interface XcodeBuildSpec {
    @Input @Optional Property<String> getConfiguration()
    @Input @Optional Property<String> getScheme()
    @InputDirectory @Optional DirectoryProperty getArchivePath()
    @OutputDirectory @Optional DirectoryProperty getDerivedDataPath()
    @InputFile @Optional RegularFileProperty getExportOptionsPlist()
    @OutputDirectory @Optional DirectoryProperty getExportPath()
    @InputDirectory @Optional DirectoryProperty getXcodeWorkspace()
    @InputDirectory @Optional DirectoryProperty getXcodeProject()
    @Input @Optional ListProperty<String> getAdditionalArguments()
}
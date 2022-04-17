package com.iptech.gradle.xcode.api

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.provider.Property

interface ExportArchiveSpec {
    @InputDirectory DirectoryProperty getArchivePath()
    @InputFile RegularFileProperty getExportOptionsPlist()
    @OutputDirectory DirectoryProperty getExportPath()
}
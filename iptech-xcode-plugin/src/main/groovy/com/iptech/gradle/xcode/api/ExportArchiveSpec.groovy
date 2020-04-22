package com.iptech.gradle.xcode.api

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile

interface ExportArchiveSpec {
    @InputDirectory DirectoryProperty getArchivePath()
    @InputFile RegularFileProperty getExportOptionsPlist()
    @OutputFile RegularFileProperty getExportPath()
}
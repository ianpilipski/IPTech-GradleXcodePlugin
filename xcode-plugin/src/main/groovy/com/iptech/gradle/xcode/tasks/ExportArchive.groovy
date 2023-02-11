package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.ExportArchiveSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.plugins.Convention
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class ExportArchive extends DefaultTask implements ExportArchiveSpec {
    @OutputFile abstract RegularFileProperty getOutput()

    @TaskAction
    File exec() {
        File f = project.xcode.exportArchive(this)
        output.set(f)
        return f
    }
}

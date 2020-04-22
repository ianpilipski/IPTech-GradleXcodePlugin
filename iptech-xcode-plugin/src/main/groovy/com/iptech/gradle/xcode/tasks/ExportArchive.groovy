package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.ExportArchiveSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.process.ExecResult

abstract class ExportArchive extends DefaultTask implements ExportArchiveSpec {
    @TaskAction
    ExecResult exec() {
        return project.xcode.exportArchive(spec)
    }
}

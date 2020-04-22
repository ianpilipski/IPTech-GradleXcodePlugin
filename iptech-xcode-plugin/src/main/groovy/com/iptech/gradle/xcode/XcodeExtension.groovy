package com.iptech.gradle.xcode

import com.iptech.gradle.xcode.api.ArchiveSpec
import com.iptech.gradle.xcode.api.ExportArchiveSpec
import com.iptech.gradle.xcode.api.ResignAppSpec
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import com.iptech.gradle.xcode.internal.executors.ArchiveExecutor
import com.iptech.gradle.xcode.internal.executors.ExportArchiveExecutor
import com.iptech.gradle.xcode.internal.executors.ResignAppExecutor
import com.iptech.gradle.xcode.internal.executors.XcodeBuildExecutor
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.process.ExecResult

class XcodeExtension {
    @Internal final Project project
    private final XcodeBuildExecutor xcodeBuildExecutor
    private final ResignAppExecutor resignAppExecutor
    private final ExportArchiveExecutor exportArchiveExecutor
    private final ArchiveExecutor archiveExecutor

    XcodeExtension(Project project) {
        this.project = project
        this.xcodeBuildExecutor = project.objects.newInstance(XcodeBuildExecutor.class)
        this.resignAppExecutor = project.objects.newInstance(ResignAppExecutor.class)
        this.exportArchiveExecutor = project.objects.newInstance(ExportArchiveExecutor.class, xcodeBuildExecutor)
        this.archiveExecutor = project.objects.newInstance(ArchiveExecutor.class, xcodeBuildExecutor)
    }

    ExecResult xcodeBuild(XcodeBuildSpec spec) {
        return xcodeBuildExecutor.exec(spec)
    }

    ExecResult exportArchive(ExportArchiveSpec spec) {
        return exportArchiveExecutor.exec(spec)
    }

    ExecResult resignApp(ResignAppSpec spec) {
        return resignAppExecutor.exec(spec)
    }

    ExecResult archive(ArchiveSpec spec) {
        return archiveExecutor.exec(spec)
    }
}

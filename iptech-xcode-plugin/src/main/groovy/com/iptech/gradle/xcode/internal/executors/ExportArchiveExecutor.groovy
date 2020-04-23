package com.iptech.gradle.xcode.internal.executors


import com.iptech.gradle.xcode.api.ExportArchiveSpec
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import org.gradle.api.model.ObjectFactory
import org.gradle.process.ExecResult

import javax.inject.Inject

class ExportArchiveExecutor {
    private final XcodeBuildExecutor xcodeBuildExecutor
    private final ObjectFactory objectFactory

    @Inject
    ExportArchiveExecutor(XcodeBuildExecutor xcodeBuildExecutor, ObjectFactory objectFactory) {
        this.xcodeBuildExecutor = xcodeBuildExecutor
        this.objectFactory = objectFactory
    }

    ExecResult exec(ExportArchiveSpec exportArchiveSpec) {
        XcodeBuildSpec spec = objectFactory.newInstance(XcodeBuildSpec.class)
        spec.archivePath = exportArchiveSpec.archivePath
        spec.exportOptionsPlist = exportArchiveSpec.exportOptionsPlist
        spec.exportPath = exportArchiveSpec.exportPath
        spec.additionalArguments.add('-exportArchive')

        return xcodeBuildExecutor.exec(spec)
    }
}

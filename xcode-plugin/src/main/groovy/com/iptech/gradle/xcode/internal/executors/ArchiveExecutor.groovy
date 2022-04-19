package com.iptech.gradle.xcode.internal.executors


import com.iptech.gradle.xcode.api.ArchiveSpec
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import org.gradle.api.model.ObjectFactory
import org.gradle.process.ExecResult

import javax.inject.Inject

class ArchiveExecutor {
    private final XcodeBuildExecutor xcodeBuildExecutor
    private final ObjectFactory objectFactory

    @Inject
    ArchiveExecutor(XcodeBuildExecutor xcodeBuildExecutor, ObjectFactory objectFactory) {
        this.xcodeBuildExecutor = xcodeBuildExecutor
        this.objectFactory = objectFactory
    }

    ExecResult exec(ArchiveSpec archiveSpec) {
        XcodeBuildSpec spec = objectFactory.newInstance(XcodeBuildSpec.class)
        spec.configuration = archiveSpec.configuration
        spec.scheme = archiveSpec.scheme
        spec.derivedDataPath = archiveSpec.derivedDataPath
        spec.projectPath = archiveSpec.projectPath
        spec.additionalArguments = archiveSpec.additionalArguments
        spec.archivePath = archiveSpec.archivePath

        if(archiveSpec.CODE_SIGN_IDENTITY.isPresent()) spec.additionalArguments.add(
            "CODE_SIGN_IDENTITY=${archiveSpec.CODE_SIGN_IDENTITY.get()}"
        )
        if(archiveSpec.CODE_SIGN_STYLE.isPresent()) spec.additionalArguments.add(
            "CODE_SIGN_STYLE=${archiveSpec.CODE_SIGN_STYLE.get()}"
        )
        if(archiveSpec.DEVELOPMENT_TEAM.isPresent()) spec.additionalArguments.add(
            "DEVELOPMENT_TEAM=${archiveSpec.DEVELOPMENT_TEAM.get()}"
        )
        if(archiveSpec.PROVISIONING_PROFILE_SPECIFIER.isPresent()) spec.additionalArguments.add(
            "PROVISIONING_PROFILE_SPECIFIER=${archiveSpec.PROVISIONING_PROFILE_SPECIFIER.get()}"
        )
        if(archiveSpec.CODE_SIGNING_REQUIRED.isPresent()) spec.additionalArguments.add(
            "CODE_SIGNING_REQUIRED=${archiveSpec.CODE_SIGNING_REQUIRED.get()}"
        )
        if(archiveSpec.CODE_SIGNING_ALLOWED.isPresent()) spec.additionalArguments.add(
            "CODE_SIGNING_ALLOWED=${archiveSpec.CODE_SIGNING_ALLOWED.get()}"
        )

        spec.additionalArguments.add('archive')
        return xcodeBuildExecutor.exec(spec)
    }
}

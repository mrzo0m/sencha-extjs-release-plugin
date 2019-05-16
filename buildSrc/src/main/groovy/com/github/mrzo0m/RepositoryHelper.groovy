package com.github.mrzo0m

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

class RepositoryHelper {
    static Repository openJGitRepository() throws IOException {
        return new FileRepositoryBuilder()
                .readEnvironment()
                .findGitDir()
                .build()
    }
}

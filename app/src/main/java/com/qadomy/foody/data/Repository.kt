package com.qadomy.foody.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDatabaseSource: LocalDatabaseSource
) {

    val remote = remoteDataSource
    val local = localDatabaseSource
}
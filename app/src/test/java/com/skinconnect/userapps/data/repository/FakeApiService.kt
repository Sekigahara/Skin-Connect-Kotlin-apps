package com.skinconnect.userapps.data.repository

import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.request.RegisterRequest
import com.skinconnect.userapps.data.remote.retrofit.ApiService
import com.skinconnect.userapps.util.DataDummy

class FakeApiService : ApiService {
    override suspend fun login(request: LoginRequest) = DataDummy.generateDummyLoginResponse()

    override suspend fun register(request: RegisterRequest) =
        DataDummy.generateDummyRegisterResponse()
}
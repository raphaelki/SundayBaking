
package com.example.rapha.sundaybaking.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.rapha.sundaybaking.AppExecutors;
import com.example.rapha.sundaybaking.data.models.Resource;

public abstract class NetworkBoundResource<ResultType, RequestType>{

    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors){
        this.appExecutors = appExecutors;
        // dispatch loading result
        result.setValue(Resource.loading(null));
        // get local data
        LiveData<ResultType> localData = loadFromDb();
        // add localData as source to MediatorLiveData
//        result.addSource(localData, );
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

}
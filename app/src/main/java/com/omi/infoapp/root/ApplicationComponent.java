package com.omi.infoapp.root;


import com.omi.infoapp.create_activity.CreateModule;
import com.omi.infoapp.create_activity.view.CreateActivity;
import com.omi.infoapp.main_activity.MainModule;
import com.omi.infoapp.main_activity.view.MainActivity;
import com.omi.infoapp.repository.dp.InfoLocalModule;
import com.omi.infoapp.repository.http.InfoApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, InfoApiModule.class ,InfoLocalModule.class ,MainModule.class, CreateModule.class})
public interface ApplicationComponent {

    void inject(MainActivity target);
    void inject(CreateActivity target);

}

package di

import data.repository.RepositoryImpl
import org.koin.dsl.module
import screen.detail.DetailViewModel
import screen.home.HomeViewModel
import screen.login.LoginViewModel
import screen.main.MainViewModel
import screen.audis.AudisViewModel
import screen.settings.SettingsViewModel
import screen.ticket.TicketViewModel
import utils.Constants.SOCKET_BASE_URL
import webSocket.AppSocket

val commonModule = module {

    single(createdAtStart = true) {
        RepositoryImpl(get())
    }

    single(createdAtStart = true) {
        LoginViewModel(get())
    }

    single(createdAtStart = true) {
        MainViewModel(get())
    }

    single(createdAtStart = true) {
        HomeViewModel(get())
    }

    single(createdAtStart = true) {
        TicketViewModel(get())
    }

    single(createdAtStart = true) {
        SettingsViewModel(get())
    }

    single(createdAtStart = true) {
        DetailViewModel(get())
    }

    single(createdAtStart = true) {
        AppSocket(SOCKET_BASE_URL, emptyMap())
    }

    single(createdAtStart = true) {
        AudisViewModel(get(), get())
    }

}
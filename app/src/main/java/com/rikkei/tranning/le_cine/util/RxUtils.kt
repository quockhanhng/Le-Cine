package com.rikkei.tranning.le_cine.util

import io.reactivex.disposables.Disposable

fun unsubscribeThis(subscription: Disposable?) {

    if (subscription != null && !subscription.isDisposed) {
        subscription.dispose()
    } // else subscription doesn't exist or already unsubscribed
}

fun unsubscribe(vararg subscriptions: Disposable) {
    for (subscription in subscriptions) {
        unsubscribeThis(subscription)
    }
}

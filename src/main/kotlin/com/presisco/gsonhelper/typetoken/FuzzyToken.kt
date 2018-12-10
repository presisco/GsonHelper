package com.presisco.gsonhelper.typetoken

import com.google.gson.reflect.TypeToken

object FuzzyToken : TypeToken<FuzzyToken.FuzzyObject>() {
    abstract class FuzzyObject : Map<String, Any?>, List<Any?>
}
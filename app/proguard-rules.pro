# Retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep,allowobfuscation interface retrofit2.Call
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class lu.post.eval.**$$serializer { *; }
-keepclassmembers class lu.post.eval.** {
    *** Companion;
}
-keepclasseswithmembers class lu.post.eval.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Models
-keep class lu.post.eval.domain.model.** { *; }
-keep class lu.post.eval.data.model.** { *; }

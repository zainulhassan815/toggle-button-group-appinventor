# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.dreamers.togglegroup.ToggleGroup {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses com/dreamers/togglegroup/repack
-flattenpackagehierarchy
-dontpreverify

-dontwarn nl.bryanderidder.themedtogglebuttongroup.**
-dontwarn com.google.android.flexbox.**
package com.dreamers.togglegroup

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.google.appinventor.components.annotations.DesignerProperty
import com.google.appinventor.components.annotations.SimpleEvent
import com.google.appinventor.components.annotations.SimpleFunction
import com.google.appinventor.components.annotations.SimpleProperty
import com.google.appinventor.components.common.PropertyTypeConstants
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent
import com.google.appinventor.components.runtime.AndroidViewComponent
import com.google.appinventor.components.runtime.ComponentContainer
import com.google.appinventor.components.runtime.EventDispatcher
import com.google.appinventor.components.runtime.util.MediaUtil
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup
import java.io.File

@Suppress("FunctionName")
@SuppressLint("NewApi")
class ToggleGroup(container: ComponentContainer) : AndroidNonvisibleComponent(container.`$form`()) {

    companion object {
        const val COLUMN = "Column"
        const val ROW = "Row"
        const val COLUMN_REVERSE = "Column Reverse"
        const val ROW_REVERSE = "Row Reverse"
        const val WRAP = "Wrap"
        const val No_WRAP = "No Wrap"
        const val WRAP_REVERSE = "Wrap Reverse"
        const val FLEX_START = "Flex Start"
        const val FLEX_END = "Flex End"
        const val CENTER = "Center"
        const val BASELINE = "Baseline"
        const val STRETCH = "Stretch"
        const val SPACE_BETWEEN = "Space Between"
        const val SPACE_AROUND = "Space Around"
        const val SPACE_EVENLY = "Space Evenly"
        const val NONE = "None"
        const val CIRCULAR_REVEAL = "Circular Reveal"
        const val FADE = "Fade"
        const val HORIZONTAL_SLIDE = "Horizontal Slide"
        const val VERTICAL_SLIDE = "Vertical Slide"
        const val HORIZONTAL_WINDOW = "Horizontal Window"
        const val VERTICAL_WINDOW = "Vertical Window"
    }

    private val context: Context = container.`$context`()
    private var buttonContainer: ThemedToggleButtonGroup? = null
    private var flexDirection: String = ROW
    private var flexWrap: String = WRAP
    private var justifyContent: String = CENTER
    private var alignItems: String = CENTER
    private var alignContent: String = CENTER
    private var maxLines: Int = -1
    private var selectAnimation: String = CIRCULAR_REVEAL
    private val logTag = "ToggleGroupExtension"
    private var spacing: Int = 8
    private var requiredAmount: Int = 1
    private var selectableAmount: Int = 1
    private var font: String = ""
    private var fontSize: Int = 16
    private var textColor: Int = Color.parseColor("#5E5E5E")
    private var selectedTextColor: Int = Color.parseColor("#FFFFFF")
    private var backgroundColor: Int = Color.parseColor("#EBEBEB")
    private var selectedBackgroundColor: Int = Color.parseColor("#5E6FED")
    private var iconSize: Int = 24.px
    private var iconSpacing: Int = 8.px
    private var textPaddingVertical: Int = 35
    private var textPaddingHorizontal: Int = 50
    private var borderColor: Int = Color.parseColor("#5e5e5e")
    private var selectedBorderColor: Int = Color.parseColor("#5e5e5e")
    private var borderRadius: Int = 155
    private var borderWidth: Int = 0
    private var selectedBorderWidth: Int = 0

    private val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    @SuppressLint("NewApi")
    private fun getFilePath(file: String) = when {
        context.javaClass.name.contains("makeroid") -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.getExternalFilesDir(null).toString() + "/assets/$file"
            } else {
                "/storage/emulated/0/Kodular/assets/$file"
            }
        }
        else -> context.getExternalFilesDir(null).toString() + "/assets/$file"
    }

    private fun applySpacing(buttons: List<ThemedButton>?) {
        buttons?.forEach { button ->
            val params = button.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 0, spacing.px, spacing.px)
        }
    }

    private fun addIcon(button: ThemedButton, icon: String? = null, selectedIcon: String? = null) {
        if (!icon.isNullOrEmpty()) {
            try {
                button.ivIcon.setImageDrawable(MediaUtil.getBitmapDrawable(form, getFilePath(icon)))
            } catch (e: Exception) {
                Log.e(logTag, "addIcon - Icon : $icon | Exception : $e")
            }
        }
        if (!selectedIcon.isNullOrEmpty()) {
            try {
                button.ivSelectedIcon.setImageDrawable(MediaUtil.getBitmapDrawable(form, getFilePath(selectedIcon)))
            } catch (e: Exception) {
                Log.e(logTag, "addIcon - Icon : $icon | Exception : $e")
            }
        }
    }

    private fun applyCardStyles(button: ThemedButton) {
        button.selectedBorderWidth = selectedBorderWidth.toFloat()
        button.borderWidth = borderWidth.toFloat()
        button.borderColor = borderColor
        button.selectedBorderColor = selectedBorderColor
        button.applyToCards { card ->
            card.cornerRadius = borderRadius.toFloat()
        }
    }

    private fun setIconButtonStyles(button: ThemedButton, unselectedIcon: String, selectedIcon: String) {
        button.applyToIcons { icon ->
            val params = FrameLayout.LayoutParams(iconSize, iconSize)
            params.apply {
                gravity = Gravity.CENTER.or(Gravity.START)
                setMargins(iconSpacing, topMargin, iconSpacing, bottomMargin)
            }
            icon.layoutParams = params
            icon.scaleType = ImageView.ScaleType.FIT_CENTER
            val padding = iconSize.times(17).div(100)
            icon.setPadding(padding, padding, padding, padding)
        }

        addIcon(button, icon = unselectedIcon, selectedIcon = selectedIcon)

        button.applyToTexts { textView ->
            val params = textView.layoutParams as FrameLayout.LayoutParams
            params.apply {
                gravity = Gravity.CENTER.or(Gravity.END)
                setMargins(2.times(iconSpacing), topMargin, rightMargin, bottomMargin)
            }
        }
    }

    private fun applyTextStyle(button: ThemedButton) {
        button.applyToTexts {
            try {
                val typeface = Typeface.createFromFile(File(getFilePath(font)))
                it.typeface = typeface
            } catch (e: Exception) {
                Log.e(logTag, "applyFont - Error : $e")
            }

            it.textSize = fontSize.toFloat()
            it.setPadding(textPaddingHorizontal, textPaddingVertical, textPaddingHorizontal, textPaddingVertical)
        }
    }

    private fun applyButtonStyles(button: ThemedButton) {
        applyTextStyle(button)
        applyCardStyles(button)
        button.bgColor = backgroundColor
        button.selectedBgColor = selectedBackgroundColor
        button.tvText.setTextColor(textColor)
        button.tvSelectedText.setTextColor(selectedTextColor)
    }

    @SimpleFunction(description = "Create a toggle button group container in a view")
    fun Create(layout: AndroidViewComponent) {

        buttonContainer = ThemedToggleButtonGroup(context)
        buttonContainer?.setOnSelectListener { OnSelected(it.tag.toString()) }

        FlexDirection(flexDirection)
        FlexWrap(flexWrap)
        JustifyContent(justifyContent)
        AlignContent(alignContent)
        AlignItems(alignItems)
        MaxLines(maxLines)

        SelectAnimation(selectAnimation)
        RequiredAmount(requiredAmount)
        SelectableAmount(selectableAmount)

        (layout.view as ViewGroup).addView(
            buttonContainer,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        defaultValue = CIRCULAR_REVEAL,
        editorArgs = [NONE, CIRCULAR_REVEAL, FADE, HORIZONTAL_SLIDE, VERTICAL_SLIDE, HORIZONTAL_WINDOW, VERTICAL_WINDOW]
    )
    @SimpleProperty(description = "Set selection animation. Default value is $CIRCULAR_REVEAL")
    fun SelectAnimation(animation: String) {
        buttonContainer?.selectAnimation = animation(animation)
        selectAnimation = animation
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_INTEGER,
        defaultValue = "8"
    )
    @SimpleProperty(description = "Set horizontal spacing. Default value is 8")
    fun Spacing(value: Int) {
        spacing = value.px
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "1"
    )
    @SimpleProperty(description = "Required selection amount. Default value is 1")
    fun RequiredAmount(amount: Int) {
        buttonContainer?.requiredAmount = amount
        requiredAmount = amount
    }

    @SimpleProperty(description = "Required selection amount")
    fun RequiredAmount() = requiredAmount

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "1"
    )
    @SimpleProperty(description = "Maximum number of items that can be selected. Default value is 1")
    fun SelectableAmount(amount: Int) {
        buttonContainer?.selectableAmount = amount
        selectableAmount = amount
    }

    @SimpleProperty(description = "Maximum number of items that can be selected")
    fun SelectableAmount() = selectableAmount

    @SimpleFunction(description = "Add a new button to toggle group. If no selected text is provided then default text is used. If no selected icon is provided then default icon is used.")
    fun Add(text: String, selectedText: String, icon: String, selectedIcon: String, tag: String) {
        val button = ThemedButton(context)
        applyButtonStyles(button)

        if (icon.isNotEmpty()) {
            setIconButtonStyles(button, icon, selectedIcon.ifEmpty { icon })
        }

        button.text = text
        button.selectedText = selectedText.ifEmpty { text }
        button.tag = tag

        buttonContainer?.addView(
            button,
            ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        )
        applySpacing(buttonContainer?.buttons)
    }

    @SimpleFunction(description = "Remove tag from button container")
    fun Remove(tag: String) {
        buttonContainer?.removeView(buttonContainer?.findViewWithTag(tag))
    }

    @SimpleProperty(description = "Get a list of selected tags")
    fun SelectedTags(): List<String> {
        return when (buttonContainer) {
            null -> emptyList()
            else -> buttonContainer!!.selectedButtons.map { it.tag.toString() }
        }
    }


    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET,
    )
    @SimpleProperty(description = "Custom font typeface")
    fun Font(asset: String) {
        font = asset
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "16"
    )
    @SimpleProperty(description = "Font size")
    fun FontSize(size: Int) {
        fontSize = size
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF5E5E5E"
    )
    @SimpleProperty(description = "Text color")
    fun TextColor(color: Int) {
        textColor = color
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFFFFFFFF"
    )
    @SimpleProperty(description = "Selected text color")
    fun SelectedTextColor(color: Int) {
        selectedTextColor = color
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFFEBEBEB"
    )
    @SimpleProperty(description = "Background color")
    fun BackgroundColor(color: Int) {
        backgroundColor = color
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF5E6FED"
    )
    @SimpleProperty(description = "Selected background color")
    fun SelectedBackgroundColor(color: Int) {
        selectedBackgroundColor = color
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "24"
    )
    @SimpleProperty(description = "Icon size")
    fun IconSize(size: Int) {
        iconSize = size.px
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "8"
    )
    @SimpleProperty(description = "Icon spacing")
    fun IconSpacing(space: Int) {
        iconSpacing = space.px
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "35"
    )
    @SimpleProperty(description = "Vertical text padding")
    fun TextPaddingVertical(value: Int) {
        textPaddingVertical = value
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "50"
    )
    @SimpleProperty(description = "Horizontal text padding")
    fun TextPaddingHorizontal(value: Int) {
        textPaddingHorizontal = value
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "0"
    )
    @SimpleProperty(description = "Border width for un-selected card")
    fun BorderWidth(width: Int) {
        borderWidth = width
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "0"
    )
    @SimpleProperty(description = "Border width for selected card")
    fun SelectedBorderWidth(width: Int) {
        selectedBorderWidth = width
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF5e5e5e"
    )
    @SimpleProperty(description = "Border color")
    fun BorderColor(color: Int) {
        borderColor = color
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF5e5e5e"
    )
    @SimpleProperty(description = "Selected border color")
    fun SelectedBorderColor(color: Int) {
        selectedBorderColor = color
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "155"
    )
    @SimpleProperty(description = "Border radius")
    fun BorderRadius(radius: Int) {
        borderRadius = radius
    }


    @SimpleEvent(description = "Event raised when item is selected")
    fun OnSelected(tag: String) {
        EventDispatcher.dispatchEvent(this, "OnSelected", tag)
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [COLUMN, ROW, COLUMN_REVERSE, ROW_REVERSE],
        defaultValue = ROW
    )
    @SimpleProperty(description = "Flex direction")
    fun FlexDirection(axis: String) {
        buttonContainer?.flexDirection = direction(axis)
        flexDirection = axis
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [WRAP, No_WRAP, WRAP_REVERSE],
        defaultValue = WRAP
    )
    @SimpleProperty(description = "Flex wrap")
    fun FlexWrap(value: String) {
        buttonContainer?.flexWrap = wrap(value)
        flexWrap = value
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [FLEX_START, FLEX_END, CENTER, SPACE_BETWEEN, SPACE_AROUND, SPACE_EVENLY],
        defaultValue = CENTER
    )
    @SimpleProperty(description = "Justify content")
    fun JustifyContent(value: String) {
        buttonContainer?.justifyContent = justifyContent(value)
        justifyContent = value
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [FLEX_START, FLEX_END, CENTER, SPACE_BETWEEN, SPACE_AROUND, STRETCH],
        defaultValue = CENTER
    )
    @SimpleProperty(description = "Align content")
    fun AlignContent(value: String) {
        buttonContainer?.alignContent = alignContent(value)
        alignContent = value
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [FLEX_START, FLEX_END, CENTER, BASELINE, STRETCH],
        defaultValue = CENTER
    )
    @SimpleProperty(description = "Align content.")
    fun AlignItems(value: String) {
        buttonContainer?.alignContent = alignItems(value)
        alignItems = value
    }

    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_INTEGER,
        defaultValue = "-1"
    )
    @SimpleProperty(description = "Set maximum number of wrap lines. Use -1 for auto")
    fun MaxLines(lines: Int) {
        buttonContainer?.maxLine = lines
        maxLines = lines
    }

    @SimpleProperty
    fun None() = NONE

    @SimpleProperty
    fun CircularReveal() = CIRCULAR_REVEAL

    @SimpleProperty
    fun Fade() = FADE

    @SimpleProperty
    fun HorizontalSlide() = HORIZONTAL_SLIDE

    @SimpleProperty
    fun VerticalSlide() = VERTICAL_SLIDE

    @SimpleProperty
    fun HorizontalWindow() = HORIZONTAL_WINDOW

    @SimpleProperty
    fun VerticalWindow() = VERTICAL_WINDOW

}

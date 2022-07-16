package com.dreamers.togglegroup

import com.google.android.flexbox.*
import nl.bryanderidder.themedtogglebuttongroup.SelectAnimation

fun animation(animation: String) : SelectAnimation = when(animation) {
    ToggleGroup.NONE -> SelectAnimation.NONE
    ToggleGroup.CIRCULAR_REVEAL -> SelectAnimation.CIRCULAR_REVEAL
    ToggleGroup.FADE -> SelectAnimation.FADE
    ToggleGroup.HORIZONTAL_SLIDE -> SelectAnimation.HORIZONTAL_SLIDE
    ToggleGroup.VERTICAL_SLIDE -> SelectAnimation.VERTICAL_SLIDE
    ToggleGroup.HORIZONTAL_WINDOW -> SelectAnimation.HORIZONTAL_WINDOW
    ToggleGroup.VERTICAL_WINDOW -> SelectAnimation.VERTICAL_WINDOW
    else -> SelectAnimation.CIRCULAR_REVEAL
}

fun direction(axis: String): Int {
    return when (axis) {
        ToggleGroup.COLUMN -> FlexDirection.COLUMN
        ToggleGroup.ROW -> FlexDirection.ROW
        ToggleGroup.COLUMN_REVERSE -> FlexDirection.COLUMN_REVERSE
        ToggleGroup.ROW_REVERSE -> FlexDirection.ROW_REVERSE
        else -> FlexDirection.COLUMN
    }
}

fun wrap(value: String): Int = when (value) {
    ToggleGroup.No_WRAP -> FlexWrap.NOWRAP
    ToggleGroup.WRAP -> FlexWrap.WRAP
    ToggleGroup.WRAP_REVERSE -> FlexWrap.WRAP_REVERSE
    else -> FlexWrap.NOWRAP
}

fun alignItems(value: String): Int = when (value) {
    ToggleGroup.FLEX_START -> AlignItems.FLEX_START
    ToggleGroup.FLEX_END -> AlignItems.FLEX_END
    ToggleGroup.CENTER -> AlignItems.CENTER
    ToggleGroup.BASELINE -> AlignItems.BASELINE
    ToggleGroup.STRETCH -> AlignItems.STRETCH
    else -> AlignItems.CENTER
}

fun alignContent(value: String): Int = when (value) {
    ToggleGroup.FLEX_START -> AlignContent.FLEX_START
    ToggleGroup.FLEX_END -> AlignContent.FLEX_END
    ToggleGroup.CENTER -> AlignContent.CENTER
    ToggleGroup.STRETCH -> AlignContent.STRETCH
    ToggleGroup.SPACE_AROUND -> AlignContent.SPACE_AROUND
    ToggleGroup.SPACE_BETWEEN -> AlignContent.SPACE_BETWEEN
    else -> AlignContent.CENTER
}

fun justifyContent(value: String): Int = when (value) {
    ToggleGroup.FLEX_START -> JustifyContent.FLEX_START
    ToggleGroup.FLEX_END -> JustifyContent.FLEX_END
    ToggleGroup.CENTER -> JustifyContent.CENTER
    ToggleGroup.SPACE_AROUND -> JustifyContent.SPACE_AROUND
    ToggleGroup.SPACE_BETWEEN -> JustifyContent.SPACE_BETWEEN
    ToggleGroup.SPACE_EVENLY -> JustifyContent.SPACE_EVENLY
    else -> JustifyContent.CENTER
}
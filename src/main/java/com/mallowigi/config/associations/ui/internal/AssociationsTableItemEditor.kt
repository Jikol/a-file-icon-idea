/*
 * The MIT License (MIT)
 *
 *  Copyright (c) 2020 Elior "Mallowigi" Boukhobza
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.mallowigi.config.associations.ui.internal

import com.intellij.util.Function
import com.intellij.util.ui.table.TableModelEditor.DialogItemEditor
import com.mallowigi.icons.associations.Association
import com.mallowigi.icons.associations.RegexAssociation
import org.jetbrains.annotations.NotNull

class AssociationsTableItemEditor : DialogItemEditor<Association> {
  override fun getItemClass(): @NotNull Class<out Association> = RegexAssociation::class.java

  override fun clone(item: Association,
                     forInPlaceEditing: Boolean): Association {
    return RegexAssociation(if (forInPlaceEditing) item.name else "",
      if (forInPlaceEditing) item.matcher else "",
      if (forInPlaceEditing) item.icon else "")
  }

  override fun edit(item: Association,
                    mutator: Function<Association, Association>,
                    isAdd: Boolean) {
    val settings = clone(item, true)
    mutator.`fun`(item).apply(settings)
  }

  override fun applyEdited(oldItem: Association,
                           newItem: Association) {
    oldItem.apply(newItem)
  }

  override fun isEditable(item: Association): Boolean = !item.isEmpty

  override fun isEmpty(item: Association): Boolean = item.isEmpty

}
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
package com.mallowigi.config.associations.ui.columns

import com.intellij.openapi.Disposable
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.ui.CollectionComboBoxModel
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.util.PathUtil
import com.intellij.util.ui.table.IconTableCellRenderer
import com.intellij.util.ui.table.TableModelEditor.EditableColumnInfo
import com.mallowigi.config.AtomSettingsBundle.message
import com.mallowigi.icons.associations.Association
import com.mallowigi.icons.associations.RegexAssociation
import com.mallowigi.icons.providers.DefaultFileIconProvider
import icons.MTIcons
import org.jetbrains.annotations.Nullable
import java.io.IOException
import javax.swing.*
import javax.swing.table.TableCellEditor
import javax.swing.table.TableCellRenderer

class IconSelectableColumnInfo(private val parent: Disposable) : EditableColumnInfo<Association?, Association>(message("AssociationsForm.folderIconsTable.columns.selectIcon")) {
  private val comboBox: JComboBox<Association>
  private val defaultCellEditor: TableCellEditor;

  init {
    val data: List<Association> = DefaultFileIconProvider.associations.getTheAssociations()
    val assocRenderer = object : SimpleListCellRenderer<Association>() {

      override fun customize(list: JList<out Association>, value: Association?, index: Int, selected: Boolean, hasFocus: Boolean) {
        text = value?.name
        icon = MTIcons.getFileIcon(value?.icon!!)
      }
    }
    comboBox = ComboBox()
    comboBox.model = CollectionComboBoxModel(data)
    comboBox.maximumRowCount = 7;
    comboBox.renderer = assocRenderer

    defaultCellEditor = DefaultCellEditor(comboBox)
  }

  override fun valueOf(item: Association?): Association? {
    return item
  }

  override fun setValue(item: Association?, value: Association?) {
    if (value != null) item?.icon = value.icon
  }


  override fun getEditor(item: Association?): @Nullable TableCellEditor? {
    return defaultCellEditor
  }

  override fun getRenderer(item: Association?): @Nullable TableCellRenderer? {
    return if (item == null || item.icon.isEmpty() || FileUtilRt.getExtension(item.icon) != "svg") {
      null
    }
    else object : IconTableCellRenderer<Association>() {
      override fun getIcon(value: Association, table: JTable, row: Int): Icon? {
        return try {
          MTIcons.getFileIcon(value.icon)
        } catch (e: IOException) {
          null
        }
      }

      override fun getText(): String {
        return PathUtil.getFileName(item.icon)
      }
    }
  }

  override fun getColumnClass(): Class<*> {
    return RegexAssociation::class.java
  }


}
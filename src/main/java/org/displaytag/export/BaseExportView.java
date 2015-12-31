/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.export;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;

/**
 * <p>
 * Base abstract class for simple export views.
 * </p>
 * <p>
 * A class that extends BaseExportView simply need to provide delimiters for rows and columns.
 * </p>
 * 
 * @author Fabrizio Giustina
 * @version $Revision: 1.19 $ ($Author: fgiust $)
 */
public abstract class BaseExportView implements TextExportView
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(BaseExportView.class);

    /**
     * TableModel to render.
     */
    private TableModel model;

    /**
     * export full list?
     */
    private boolean exportFull;

    /**
     * include header in export?
     */
    private boolean header;

    /**
     * decorate export?
     */
    private boolean decorated;

    /**
     * @see org.displaytag.export.ExportView#setParameters(org.displaytag.model.TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues)
    {
        this.model = tableModel;
        this.exportFull = exportFullList;
        this.header = includeHeader;
        this.decorated = decorateValues;
    }

    /**
     * String to add before a row.
     * 
     * @return String
     */
    protected String getRowStart()
    {
        return null;
    }

    /**
     * String to add after a row.
     * 
     * @return String
     */
    protected String getRowEnd()
    {
        return null;
    }

    /**
     * String to add before a cell.
     * 
     * @return String
     */
    protected String getCellStart()
    {
        return null;
    }

    /**
     * String to add after a cell.
     * 
     * @return String
     */
    protected abstract String getCellEnd();

    /**
     * String to add to the top of document.
     * 
     * @return String
     */
    protected String getDocumentStart()
    {
        return null;
    }

    /**
     * String to add to the end of document.
     * 
     * @return String
     */
    protected String getDocumentEnd()
    {
        return null;
    }

    /**
     * always append cell end string?
     * 
     * @return boolean
     */
    protected abstract boolean getAlwaysAppendCellEnd();

    /**
     * always append row end string?
     * 
     * @return boolean
     */
    protected abstract boolean getAlwaysAppendRowEnd();

    /**
     * can be implemented to escape values for different output.
     * 
     * @param value original column value
     * @return escaped column value
     */
    protected String escapeColumnValue(Object value)
    {
        if (value == null)
        {
            return "";
        }

        String retorno = value.toString();

        // Substituições
        retorno = retorno.replaceAll("\t", " ");
        retorno = retorno.replaceAll("\n", " ");
        retorno = retorno.replaceAll("\r", " ");

        int posicaoInicial;
        int posicaoFinal;

        // <a ..... >
        String[] htmlTags = { "<a", "<img", "</" };
        String delimitadorHtml = ">";
        for (int i = 0; i < htmlTags.length; i++)
        {
            do
            {
                posicaoFinal = -1;
                posicaoInicial = retorno.indexOf(htmlTags[i]);
                if (posicaoInicial > -1)
                {
                    posicaoFinal = retorno.indexOf(delimitadorHtml, posicaoInicial);
                    if (posicaoFinal > -1)
                    {
                        retorno = retorno.substring(0, posicaoInicial) + retorno.substring(posicaoFinal + 1);
                    }
                }
            }
            while (posicaoInicial > -1 && posicaoFinal > -1);
        }

        // Remover espacos repetidos
        while (retorno.indexOf("  ") > -1)
        {
            retorno = retorno.replaceAll("  ", " ");
        }

        return retorno.trim();
    }

    /**
     * Write table header.
     * 
     * @return String rendered header
     */
    protected String doHeaders()
    {

        final String ROW_START = this.getRowStart();
        final String ROW_END = this.getRowEnd();
        final String CELL_START = this.getCellStart();
        final String CELL_END = this.getCellEnd();
        final boolean ALWAYS_APPEND_CELL_END = this.getAlwaysAppendCellEnd();

        StringBuffer buffer = new StringBuffer(1000);

        Iterator iterator = this.model.getHeaderCellList().iterator();

        // start row
        if (ROW_START != null)
        {
            buffer.append(ROW_START);
        }

        while (iterator.hasNext())
        {
            HeaderCell headerCell = (HeaderCell) iterator.next();

            if (!headerCell.getExport())
            {
                continue;
            }

            String columnHeader = headerCell.getTitle();

            if (columnHeader == null)
            {
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            columnHeader = this.escapeColumnValue(columnHeader);

            if (CELL_START != null)
            {
                buffer.append(CELL_START);
            }

            if (columnHeader != null)
            {
                buffer.append(columnHeader);
            }

            if (CELL_END != null && (ALWAYS_APPEND_CELL_END || iterator.hasNext()))
            {
                buffer.append(CELL_END);
            }
        }

        // end row
        if (ROW_END != null)
        {
            buffer.append(ROW_END);
        }

        return buffer.toString();

    }

    /**
     * @see org.displaytag.export.TextExportView#doExport(java.io.Writer)
     */
    public void doExport(Writer out) throws IOException, JspException
    {
        if (log.isDebugEnabled())
        {
            log.debug(this.getClass().getName());
        }

        final String DOCUMENT_START = this.getDocumentStart();
        final String DOCUMENT_END = this.getDocumentEnd();
        final String ROW_START = this.getRowStart();
        final String ROW_END = this.getRowEnd();
        final String CELL_START = this.getCellStart();
        final String CELL_END = this.getCellEnd();
        final boolean ALWAYS_APPEND_CELL_END = this.getAlwaysAppendCellEnd();
        final boolean ALWAYS_APPEND_ROW_END = this.getAlwaysAppendRowEnd();

        // document start
        this.write(out, DOCUMENT_START);

        if (this.header)
        {
            this.write(out, this.doHeaders());
        }

        // get the correct iterator (full or partial list according to the exportFull field)
        RowIterator rowIterator = this.model.getRowIterator(this.exportFull);

        // iterator on rows
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();

            if (this.model.getTableDecorator() != null)
            {

                String stringStartRow = this.model.getTableDecorator().startRow();
                this.write(out, stringStartRow);
            }

            // iterator on columns
            ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

            this.write(out, ROW_START);

            boolean primeiraColuna = true;
            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();

                if (!column.getExport())
                {
                    continue;
                }

                if (!primeiraColuna)
                {
                    this.write(out, CELL_END);
                }
                else
                {
                    primeiraColuna = false;
                }

                // Get the value to be displayed for the column
                String value = this.escapeColumnValue(column.getValue(this.decorated));

                this.write(out, CELL_START);

                this.write(out, value);

            }
            if (!primeiraColuna)
            {
                if (ALWAYS_APPEND_CELL_END)
                {
                    this.write(out, CELL_END);
                }
            }
            if (ALWAYS_APPEND_ROW_END || rowIterator.hasNext())
            {
                this.write(out, ROW_END);
            }
        }

        // document end
        this.write(out, DOCUMENT_END);

    }

    /**
     * Write a String, checking for nulls value.
     * 
     * @param out output writer
     * @param string String to be written
     * @throws IOException thrown by out.write
     */
    private void write(Writer out, String string) throws IOException
    {
        if (string != null)
        {
            out.write(string);
        }
    }

    /**
     * @see org.displaytag.export.TextExportView#outputPage()
     */
    public boolean outputPage()
    {
        return false;
    }
}
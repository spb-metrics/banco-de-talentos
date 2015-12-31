/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 C�mara dos Deputados, Brasil
 * 
 * Este arquivo � parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS � um software livre; voc� pode redistribu�-lo e/ou modific�-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a.
 *
 * Este programa � distribu�do na esperan�a que possa ser �til, mas SEM NENHUMA GARANTIA; sem uma garantia impl�cita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

package br.gov.camara.util.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.exception.CDException;

public class ExibicaoDocumento
{
    private boolean asAttachment = false;
    private boolean fromCache = false;
    private String nomeArquivo = null;
    private String tipoConteudo = null;
    private String extensaoArquivo = null;

    public String getTipoConteudo()
    {
        return tipoConteudo;
    }

    public ExibicaoDocumento setTipoConteudo(String tipoConteudo)
    {
        this.tipoConteudo = tipoConteudo;
        return this;
    }

    public boolean getAsAttachment()
    {
        return asAttachment;
    }

    public ExibicaoDocumento setAsAttachment(boolean asAttachment)
    {
        this.asAttachment = asAttachment;
        return this;
    }

    public boolean getFromCache()
    {
        return fromCache;
    }

    public ExibicaoDocumento setFromCache(boolean cache)
    {
        this.fromCache = cache;
        return this;
    }

    public String getNomeArquivo()
    {
        return nomeArquivo;
    }

    public ExibicaoDocumento setNomeArquivo(String fileName)
    {
        this.nomeArquivo = fileName;
        return this;
    }

    /**
     * Configura a response de acordo com as propriedades definidas
     * @param response
     * @return
     */
    public ExibicaoDocumento gerarExibicaoDocumento(HttpServletResponse response, byte[] documento) throws CDException
    {
        ByteArrayOutputStream arquivo = new ByteArrayOutputStream();
        arquivo.write(documento, 0, documento.length);

        // Limpar o response existente (se for o caso)
        response.reset();
        response.resetBuffer();

        // Tamanho da resposta
        response.setContentLength(arquivo.size());

        String contentDisposition = null;
        // As Attachment or in Document ?
        if (asAttachment)
        {
            contentDisposition = "attachment";
        }
        else
        {
            contentDisposition = "inline";
        }
        // Nome do documento
        String nomeDocumento = this.nomeArquivo;
        if ((nomeDocumento == null) || "".equals(nomeDocumento))
        {
            if (extensaoArquivo == null)
            {
                throw new CDException("O nome do arquivo ou sua extens�o deve ser informado.");
            }
            nomeDocumento = DataNova.obterDataHoraAtual();
            nomeDocumento = nomeDocumento.substring(6, 10)
                    + nomeDocumento.substring(3, 5)
                    + nomeDocumento.substring(0, 2)
                    + nomeDocumento.substring(11, 13)
                    + nomeDocumento.substring(14, 16)
                    + nomeDocumento.substring(17, 19)
                    + extensaoArquivo;
        }
        contentDisposition += "; filename=\"" + nomeDocumento + "\"";
        response.setHeader("Content-Disposition", contentDisposition);

        // Tipo de conte�do
        String tipoConteudo = this.tipoConteudo;
        if ((tipoConteudo == null) || "".equals(tipoConteudo))
        {
            throw new CDException("O tipo de conte�do do documento deve ser especificado.");
        }
        response.setContentType(tipoConteudo);

        response.setHeader("Pragma", "public");
        // Permitir recupera��o do cache ?
        if (!fromCache)
        {
            response.setHeader("Cache-Control", "must-revalidate; post-check=0; pre-check=0");
            response.setHeader("Expires", "-1");
        }

        try
        {
            ServletOutputStream out;
            out = response.getOutputStream();
            arquivo.writeTo(out);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            throw new CDException(e);
        }

        return this;
    }

    public String getExtensaoArquivo()
    {
        return extensaoArquivo;
    }

    public ExibicaoDocumento setExtensaoArquivo(String extensaoArquivo)
    {
        this.extensaoArquivo = extensaoArquivo;
        return this;
    }
}

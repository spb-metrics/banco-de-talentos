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

package br.gov.camara.negocio.bancotalentos.facade;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.GrupoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.TalentoDAO;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;

/**
 * Facade para atributo de talento
 */
public class CategoriaTalentoFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(CategoriaTalentoFacade.class);

    /**
     * Obt�m o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistros() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objCategoriaTalentoDAO.obterTotalRegistros();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return intTotalRegistros;
    }

    /**
     * Obt�m os registros de determinada p�gina
     *
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objCategoriaTalentoDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public CategoriaTalento obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        CategoriaTalento objCategoriaTalento = null;
        try
        {
            objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objCategoriaTalento;
    }

    /**
     * Inclui um registro
     *
     * @param objCategoriaTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        String strChave = null;
        try
        {
            objCategoriaTalento.setSequencialOrdenacao(new Integer(objCategoriaTalentoDAO.obterUltimoSequencialOrdenacao() + 1));
            strChave = objCategoriaTalentoDAO.incluir(objCategoriaTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return strChave;
    }

    /**
     * Obt�m os grupos dispon�veis
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterGrupos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        GrupoDAO objGrupoDAO = new GrupoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objGrupoDAO.obterTodos();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obt�m os grupos dispon�veis para determinada categoria de talento
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterGruposDisponiveis(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objCategoriaTalentoDAO.obterGruposDisponiveis(objCategoriaTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obt�m os grupos selecionados para determinada categoria de talento
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterGruposSelecionados(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objCategoriaTalentoDAO.obterGruposSelecionados(objCategoriaTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obt�m todos os registros 
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m todos os registros
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objCategoriaTalentoDAO.obterTodos();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Ordena as categorias de talento de acordo com a lista passada 
     * 
     * @param lstOrdenacao Lista contendo as categorias de talentos a serem ordenadas
     * 
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public void ordenar(List lstOrdenacao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        try
        {
            // Instancia DAO 
            CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();

            // Itera lista
            Iterator itrOrdenacao = lstOrdenacao.iterator();
            int intSequencialOrdenacao = 0;
            while (itrOrdenacao.hasNext())
            {
                CategoriaTalento objCategoriaTalento = (CategoriaTalento) itrOrdenacao.next();
                objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(String.valueOf(objCategoriaTalento.getIdentificador()));
                objCategoriaTalento.setSequencialOrdenacao(new Integer(++intSequencialOrdenacao));
                objCategoriaTalentoDAO.alterar(objCategoriaTalento);
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Altera um registro
     *
     * @param objCategoriaTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void alterar(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        try
        {
            // Se a categoria for perfil,  verifica se h� atributos relacionados � categoria com forma��o de 
            // descri��o marcada, o que n�o � permitido
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade())
                    && objCategoriaTalentoDAO.verificarAtributosAssociadosComFormacaoDescricao(objCategoriaTalento))
            {
                throw new CDException("N�o � permitido que uma categoria seja perfil,"
                        + " se existem atributos que fa�am parte da descri��o do talento para a categoria em quest�o");
            }

            // Altera
            objCategoriaTalentoDAO.alterar(objCategoriaTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Exclui um registro
     *
     * @param objCategoriaTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        TalentoDAO objTalentoDAO = new TalentoDAO();
        try
        {
            if (objCategoriaTalentoDAO.verificarExistenciaAtributoTalento(objCategoriaTalento))
            {
                throw new CDException("N�o � permitido que uma categoria que tenha atributos seja exclu�da");
            }
            objTalentoDAO.excluirPorCategoriaTalento(objCategoriaTalento);
            objCategoriaTalentoDAO.excluir(objCategoriaTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

}

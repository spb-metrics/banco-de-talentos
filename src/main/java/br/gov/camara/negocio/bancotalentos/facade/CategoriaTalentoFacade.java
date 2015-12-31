/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 Câmara dos Deputados, Brasil
 * 
 * Este arquivo é parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); na versão 2 da Licença.
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título "LICENCA.txt", junto com este programa, se não, escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
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
    // Variáveis de instância
    private static Log log = LogFactory.getLog(CategoriaTalentoFacade.class);

    /**
     * Obtém o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistros() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
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
     * Obtém os registros de determinada página
     *
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
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
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CategoriaTalento obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
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
     * @param objCategoriaTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
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
     * Obtém os grupos disponíveis
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterGrupos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
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
     * Obtém os grupos disponíveis para determinada categoria de talento
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterGruposDisponiveis(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
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
     * Obtém os grupos selecionados para determinada categoria de talento
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterGruposSelecionados(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
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
     * Obtém todos os registros 
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém todos os registros
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
     * @throws CDException se ocorrer algum erro relacionado ao negócio
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
     * @param objCategoriaTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
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
            // Se a categoria for perfil,  verifica se há atributos relacionados à categoria com formação de 
            // descrição marcada, o que não é permitido
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade())
                    && objCategoriaTalentoDAO.verificarAtributosAssociadosComFormacaoDescricao(objCategoriaTalento))
            {
                throw new CDException("Não é permitido que uma categoria seja perfil,"
                        + " se existem atributos que façam parte da descrição do talento para a categoria em questão");
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
     * @param objCategoriaTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
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
                throw new CDException("Não é permitido que uma categoria que tenha atributos seja excluída");
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

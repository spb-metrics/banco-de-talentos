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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoValoradoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaAtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.TalentoDAO;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;

/**
 * Facade para atributo de talento
 */
public class CategoriaAtributoTalentoFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(CategoriaAtributoTalentoFacade.class);

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
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objCategoriaAtributoTalentoDAO.obterTotalRegistros();
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
     * Obtém o total de registros relacionados a determinada categoria de talento
     *
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistrosPorCategoriaTalento(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objCategoriaAtributoTalentoDAO.obterTotalRegistrosPorCategoriaTalento(objCategoriaTalento);
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
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objCategoriaAtributoTalentoDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
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
     * Obtém os registros de determinada página relacionados a determinada categoria de talento
     *
     * @param objCategoriaTalento Categoria de talento desejado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPorCategoriaTalentoPorPagina(CategoriaTalento objCategoriaTalento, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objCategoriaAtributoTalentoDAO.obterPorPaginaPorCategoriaTalento(objCategoriaTalento, intNumeroPagina, intMaximoPagina);
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(lstRetorno);
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
     * @return CategoriaAtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CategoriaAtributoTalento obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        CategoriaAtributoTalento objCategoriaAtributoTalento = null;
        try
        {
            objCategoriaAtributoTalento = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPelaChave(strChave);
            objCategoriaAtributoTalentoDAO.inicializarCategoriaTalento(objCategoriaAtributoTalento);
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(objCategoriaAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objCategoriaAtributoTalento;
    }

    /**
     * Obtém uma categoria de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CategoriaTalento obterCategoriaTalentoPelaChave(String strChave) throws CDException
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
     * Obtém os atributos de talento
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterAtributosTalento() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        AtributoTalentoDAO objAtributoTalentoDAO = new AtributoTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objAtributoTalentoDAO.obterTodos();
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
     * Obtém todos os registros relacionados a determinada categoria de talento
     *
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterTodosPorCategoriaTalento(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objCategoriaAtributoTalentoDAO.obterPorCategoriaTalento(objCategoriaTalento);
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(lstRetorno);
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
     * Obtém hierarquia superior da categoria/atributo indicada
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterHierarquiaSuperior(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstRetorno = new ArrayList();
        try
        {
            if (objCategoriaAtributoTalento.getAtributoTalento().getAtributoTalentoPai() != null)
            {
                CategoriaAtributoTalento objCategoriaAtributoTalentoPai = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPorCategoriaTalentoAtributoTalento(objCategoriaAtributoTalento.getCategoriaTalento(), objCategoriaAtributoTalento.getAtributoTalento().getAtributoTalentoPai());
                if (objCategoriaAtributoTalentoPai != null)
                {
                    lstRetorno = obterHierarquiaSuperior(objCategoriaAtributoTalentoPai);
                    lstRetorno.add(objCategoriaAtributoTalentoPai);
                }
            }
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(lstRetorno);
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
     * Obtém hierarquia inferior da categoria/atributo indicada
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterHierarquiaInferior(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstRetorno = new ArrayList();
        try
        {
            List lstCategoriaAtributosTalentoFilhos = objCategoriaAtributoTalentoDAO.obterTodosOsFilhos(objCategoriaAtributoTalento);
            if (lstCategoriaAtributosTalentoFilhos != null)
            {
                Iterator itrCategoriaAtributosTalentoFilhos = lstCategoriaAtributosTalentoFilhos.iterator();
                while (itrCategoriaAtributosTalentoFilhos.hasNext())
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalentoFilho = (CategoriaAtributoTalento) itrCategoriaAtributosTalentoFilhos.next();
                    lstRetorno = obterHierarquiaInferior(objCategoriaAtributoTalentoFilho);
                    lstRetorno.add(objCategoriaAtributoTalentoFilho);
                }
            }
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(lstRetorno);
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
     * Obtém toda a hierarquia da categoria/atributo indicada (inclusive ela)
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterHierarquia(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém hierarquias inferior e superior e adiciona a passada
        List lstRetorno = new ArrayList();

        try
        {
            lstRetorno.addAll(obterHierarquiaSuperior(objCategoriaAtributoTalento));
            lstRetorno.add(objCategoriaAtributoTalento);
            lstRetorno.addAll(obterHierarquiaInferior(objCategoriaAtributoTalento));
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
     * Inclui um registro
     *
     * @param objCategoriaAtributoTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        String strChave = null;

        // Instancia DAO e inclui registro
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        // AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        AtributoTalentoDAO objAtributoTalentoDAO = new AtributoTalentoDAO();
        try
        {
            // Se o atributo formar descrição, o mesmo deve ser obrigatório
            if ("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()))
            {
                if ("N".equals(objCategoriaAtributoTalento.getIndicativoObrigatoriedade()))
                {
                    throw new CDException("Não é permitido que um atributo faça parte da descrição do talento " + "se o mesmo não for obrigatório");
                }
            }

            // Se o atributo formar descrição, a categoria a que ele pertence não pode ser perfil
            CategoriaTalento objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(String.valueOf(objCategoriaAtributoTalento.getCategoriaTalento().getIdentificador()));
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade()))
            {
                if ("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()))
                {
                    throw new CDException("Não é permitido que um atributo faça parte da descrição do talento "
                            + "se a categoria a qual ele pertence é uma categoria perfil");
                }
            }

            // Se o atributo em questão tiver um pai, ou algum filho, não pode existir o mesmo atributo 
            // mais de uma vez para a categoria
            if (objCategoriaAtributoTalento.getAtributoTalento().getAtributoTalentoPai() != null
                    || objAtributoTalentoDAO.verificarExistenciaFilhos(objCategoriaAtributoTalento.getAtributoTalento()))
            {
                if (objCategoriaAtributoTalentoDAO.verificarExistenciaAtributoRelacionado(objCategoriaTalento, objCategoriaAtributoTalento.getAtributoTalento()))
                {
                    throw new CDException("Não é permitido que um atributo faça parte de uma hierarquia "
                            + "seja relacionado mais de uma vez para a mesma categoria");
                }
            }

            // Verifica se o atributo possui um pai, e se possuir, o inclui também, caso ele não esteja associado
            AtributoTalento objAtributoTalento = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(String.valueOf(objCategoriaAtributoTalento.getAtributoTalento().getIdentificador()));
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                if (!objCategoriaAtributoTalentoDAO.verificarExistenciaAtributoRelacionado(objCategoriaTalento, objAtributoTalento.getAtributoTalentoPai()))
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalentoPai = new CategoriaAtributoTalento();
                    objCategoriaAtributoTalentoPai.setCategoriaTalento(objCategoriaTalento);
                    objCategoriaAtributoTalentoPai.setAtributoTalento(objAtributoTalento.getAtributoTalentoPai());
                    objCategoriaAtributoTalentoPai.setIndicativoObrigatoriedade(objCategoriaAtributoTalento.getIndicativoObrigatoriedade());
                    objCategoriaAtributoTalentoPai.setFormacaoDescricao(objCategoriaAtributoTalento.getFormacaoDescricao());
                    objCategoriaAtributoTalentoPai.setDicaPreenchimento("");
                    objCategoriaAtributoTalentoPai.setApelido("");
                    incluir(objCategoriaAtributoTalentoPai);
                }
            }

            // Seta ordenação
            objCategoriaAtributoTalento.setSequencialOrdenacao(new Integer(objCategoriaAtributoTalentoDAO.obterUltimoSequencialOrdenacao(objCategoriaAtributoTalento.getCategoriaTalento()) + 1));

            // Inclui
            strChave = objCategoriaAtributoTalentoDAO.incluir(objCategoriaAtributoTalento);
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
     * Ordena as categorias/atributos de talento de acordo com a lista passada 
     * 
     * @param lstOrdenacao Lista contendo as categorias/atributos de talentos a serem ordenadas
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
            CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();

            // Itera lista
            Iterator itrOrdenacao = lstOrdenacao.iterator();
            int intSequencialOrdenacao = 0;
            while (itrOrdenacao.hasNext())
            {
                CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) itrOrdenacao.next();
                objCategoriaAtributoTalento = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPelaChave(String.valueOf(objCategoriaAtributoTalento.getIdentificador()));
                objCategoriaAtributoTalento.setSequencialOrdenacao(new Integer(++intSequencialOrdenacao));
                objCategoriaAtributoTalentoDAO.alterar(objCategoriaAtributoTalento);
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
     * @param objCategoriaAtributoTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        try
        {
            // Se o atributo formar descrição, o mesmo deve ser obrigatório
            if ("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()))
            {
                if ("N".equals(objCategoriaAtributoTalento.getIndicativoObrigatoriedade()))
                {
                    throw new CDException("Não é permitido que um atributo faça parte da descrição do talento " + "se o mesmo não for obrigatório");
                }
            }

            // Se o atributo formar descrição, a categoria a que ele pertence não pode ser perfil
            CategoriaTalento objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(String.valueOf(objCategoriaAtributoTalento.getCategoriaTalento().getIdentificador()));
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade()))
            {
                if ("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()))
                {
                    throw new CDException("Não é permitido que um atributo faça parte da descrição do talento "
                            + "se a categoria a qual ele pertence é uma categoria perfil");
                }
            }

            // Se o atributo for obrigatório verifica se há valorações para ele, o que não permitirá 
            // que ele se torne obrigatório
            if (objCategoriaAtributoTalento.isBlnMudancaIndicativoObrigatoriedade() && "S".equals(objCategoriaAtributoTalento.getIndicativoObrigatoriedade()))
            {
                if (objAtributoTalentoValoradoDAO.obterTotalRegistrosPorCategoriaAtributoTalento(objCategoriaAtributoTalento) > 0)
                {
                    throw new CDException("Não é permitido marcar um atributo como obrigatório se existem" + " valorações para ele");
                }
            }

            // Altera
            objCategoriaAtributoTalentoDAO.alterar(objCategoriaAtributoTalento);
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
     * @param objCategoriaAtributoTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(CategoriaAtributoTalento objCategoriaAtributoTalento, String strConfirma) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        TalentoDAO objTalentoDAO = new TalentoDAO();
        // AtributoTalentoDAO objAtributoTalentoDAO = new AtributoTalentoDAO();
        try
        {
            // Verifica se o atributo possui algum filho associado, e se possuir, não permite a exclusão
            objCategoriaAtributoTalento = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPelaChave(String.valueOf(objCategoriaAtributoTalento.getIdentificador()));
            if (objCategoriaAtributoTalentoDAO.verificarExistenciaCategoriaAtributosTalentoFilhos(objCategoriaAtributoTalento))
            {
                throw new CDException("Para excluir este atributo relacionado, é necessário antes " + "excluir o atributo pai também relacionado");
            }

            // Verifica a quantidade de talentos valorados para o atributo a ser excluído
            int intTotalTalentosValorados = 0;
            Iterator itrCagoriaAtributoTalento = objCategoriaAtributoTalento.getAtributosTalentoValorados().iterator();
            while (itrCagoriaAtributoTalento.hasNext())
            {
                // Verifica se o atributo associado possui valoração
                AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrCagoriaAtributoTalento.next();
                if (objAtributoTalentoValorado.getValoracao() != null && !"".equals(objAtributoTalentoValorado.getValoracao()))
                {
                    intTotalTalentosValorados++;
                }
            }

            // Verifica se existem talentos valorados associados ao atributo e se está confirmando a exclusão
            if ((intTotalTalentosValorados > 0) && (strConfirma == null))
            {
                throw new CDException("Este atributo possui "
                        + intTotalTalentosValorados
                        + " talento(s) associado(s), "
                        + "caso seja excluído informações serão perdidas - Confirma exclusão?");
            }

            // Inicia transação
            DAO.iniciarTransacao();

            // Exclui valorações correspondentes a esta categoria/atributo
            objAtributoTalentoValoradoDAO.excluirPorCategoriaAtributoTalento(objCategoriaAtributoTalento);

            // Verifica se ainda há atributos para a categoria atual, se não houver, exclui os talentos associados
            if (objCategoriaAtributoTalentoDAO.obterTotalRegistrosPorCategoriaTalento(objCategoriaAtributoTalento.getCategoriaTalento()) == 0)
            {
                objTalentoDAO.excluirPorCategoriaAtributoTalento(objCategoriaAtributoTalento);
            }

            // Exclui categoria/atributo
            objCategoriaAtributoTalentoDAO.excluir(objCategoriaAtributoTalento);

            // Finaliza transação
            DAO.realizarTransacao();
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }
}

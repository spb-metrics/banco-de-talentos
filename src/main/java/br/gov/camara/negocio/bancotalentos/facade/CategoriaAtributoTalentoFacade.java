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
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(CategoriaAtributoTalentoFacade.class);

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
     * Obt�m o total de registros relacionados a determinada categoria de talento
     *
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistrosPorCategoriaTalento(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os registros de determinada p�gina relacionados a determinada categoria de talento
     *
     * @param objCategoriaTalento Categoria de talento desejado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPorCategoriaTalentoPorPagina(CategoriaTalento objCategoriaTalento, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaAtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public CategoriaAtributoTalento obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
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
     * Obt�m uma categoria de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public CategoriaTalento obterCategoriaTalentoPelaChave(String strChave) throws CDException
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
     * Obt�m os atributos de talento
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterAtributosTalento() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
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
     * Obt�m todos os registros relacionados a determinada categoria de talento
     *
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTodosPorCategoriaTalento(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m hierarquia superior da categoria/atributo indicada
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterHierarquiaSuperior(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m hierarquia inferior da categoria/atributo indicada
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterHierarquiaInferior(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m toda a hierarquia da categoria/atributo indicada (inclusive ela)
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterHierarquia(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m hierarquias inferior e superior e adiciona a passada
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
     * @param objCategoriaAtributoTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
            // Se o atributo formar descri��o, o mesmo deve ser obrigat�rio
            if ("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()))
            {
                if ("N".equals(objCategoriaAtributoTalento.getIndicativoObrigatoriedade()))
                {
                    throw new CDException("N�o � permitido que um atributo fa�a parte da descri��o do talento " + "se o mesmo n�o for obrigat�rio");
                }
            }

            // Se o atributo formar descri��o, a categoria a que ele pertence n�o pode ser perfil
            CategoriaTalento objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(String.valueOf(objCategoriaAtributoTalento.getCategoriaTalento().getIdentificador()));
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade()))
            {
                if ("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()))
                {
                    throw new CDException("N�o � permitido que um atributo fa�a parte da descri��o do talento "
                            + "se a categoria a qual ele pertence � uma categoria perfil");
                }
            }

            // Se o atributo em quest�o tiver um pai, ou algum filho, n�o pode existir o mesmo atributo 
            // mais de uma vez para a categoria
            if (objCategoriaAtributoTalento.getAtributoTalento().getAtributoTalentoPai() != null
                    || objAtributoTalentoDAO.verificarExistenciaFilhos(objCategoriaAtributoTalento.getAtributoTalento()))
            {
                if (objCategoriaAtributoTalentoDAO.verificarExistenciaAtributoRelacionado(objCategoriaTalento, objCategoriaAtributoTalento.getAtributoTalento()))
                {
                    throw new CDException("N�o � permitido que um atributo fa�a parte de uma hierarquia "
                            + "seja relacionado mais de uma vez para a mesma categoria");
                }
            }

            // Verifica se o atributo possui um pai, e se possuir, o inclui tamb�m, caso ele n�o esteja associado
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

            // Seta ordena��o
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
     * @param objCategoriaAtributoTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
            // Se o atributo formar descri��o, o mesmo deve ser obrigat�rio
            if ("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()))
            {
                if ("N".equals(objCategoriaAtributoTalento.getIndicativoObrigatoriedade()))
                {
                    throw new CDException("N�o � permitido que um atributo fa�a parte da descri��o do talento " + "se o mesmo n�o for obrigat�rio");
                }
            }

            // Se o atributo formar descri��o, a categoria a que ele pertence n�o pode ser perfil
            CategoriaTalento objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(String.valueOf(objCategoriaAtributoTalento.getCategoriaTalento().getIdentificador()));
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade()))
            {
                if ("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()))
                {
                    throw new CDException("N�o � permitido que um atributo fa�a parte da descri��o do talento "
                            + "se a categoria a qual ele pertence � uma categoria perfil");
                }
            }

            // Se o atributo for obrigat�rio verifica se h� valora��es para ele, o que n�o permitir� 
            // que ele se torne obrigat�rio
            if (objCategoriaAtributoTalento.isBlnMudancaIndicativoObrigatoriedade() && "S".equals(objCategoriaAtributoTalento.getIndicativoObrigatoriedade()))
            {
                if (objAtributoTalentoValoradoDAO.obterTotalRegistrosPorCategoriaAtributoTalento(objCategoriaAtributoTalento) > 0)
                {
                    throw new CDException("N�o � permitido marcar um atributo como obrigat�rio se existem" + " valora��es para ele");
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
     * @param objCategoriaAtributoTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
            // Verifica se o atributo possui algum filho associado, e se possuir, n�o permite a exclus�o
            objCategoriaAtributoTalento = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPelaChave(String.valueOf(objCategoriaAtributoTalento.getIdentificador()));
            if (objCategoriaAtributoTalentoDAO.verificarExistenciaCategoriaAtributosTalentoFilhos(objCategoriaAtributoTalento))
            {
                throw new CDException("Para excluir este atributo relacionado, � necess�rio antes " + "excluir o atributo pai tamb�m relacionado");
            }

            // Verifica a quantidade de talentos valorados para o atributo a ser exclu�do
            int intTotalTalentosValorados = 0;
            Iterator itrCagoriaAtributoTalento = objCategoriaAtributoTalento.getAtributosTalentoValorados().iterator();
            while (itrCagoriaAtributoTalento.hasNext())
            {
                // Verifica se o atributo associado possui valora��o
                AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrCagoriaAtributoTalento.next();
                if (objAtributoTalentoValorado.getValoracao() != null && !"".equals(objAtributoTalentoValorado.getValoracao()))
                {
                    intTotalTalentosValorados++;
                }
            }

            // Verifica se existem talentos valorados associados ao atributo e se est� confirmando a exclus�o
            if ((intTotalTalentosValorados > 0) && (strConfirma == null))
            {
                throw new CDException("Este atributo possui "
                        + intTotalTalentosValorados
                        + " talento(s) associado(s), "
                        + "caso seja exclu�do informa��es ser�o perdidas - Confirma exclus�o?");
            }

            // Inicia transa��o
            DAO.iniciarTransacao();

            // Exclui valora��es correspondentes a esta categoria/atributo
            objAtributoTalentoValoradoDAO.excluirPorCategoriaAtributoTalento(objCategoriaAtributoTalento);

            // Verifica se ainda h� atributos para a categoria atual, se n�o houver, exclui os talentos associados
            if (objCategoriaAtributoTalentoDAO.obterTotalRegistrosPorCategoriaTalento(objCategoriaAtributoTalento.getCategoriaTalento()) == 0)
            {
                objTalentoDAO.excluirPorCategoriaAtributoTalento(objCategoriaAtributoTalento);
            }

            // Exclui categoria/atributo
            objCategoriaAtributoTalentoDAO.excluir(objCategoriaAtributoTalento);

            // Finaliza transa��o
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

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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoOpcaoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaAtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.ConsultaTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.PessoaDAO;
import br.gov.camara.negocio.bancotalentos.dao.TalentoDAO;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcao;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.comum.pojo.TipoHTML;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.seguranca.UsuarioAutenticado;

/**
 * Facade para a consulta de talentos
 */
public class ConsultaTalentoFacade
{

    // Variáveis de instância
    private static Log log = LogFactory.getLog(ConsultaTalentoFacade.class);

    public static final Integer ID_CATEGORIA_DADOS_PESSOAIS = new Integer(-1);

    public static final Integer ID_ATRIBUTO_NOME = new Integer(-1);

    public static final Integer ID_ATRIBUTO_IDADE = new Integer(-2);

    public static final Integer ID_ATRIBUTO_SEXO = new Integer(-3);
    public static final Integer ID_OPCAO_SEXO_MASCULINO = new Integer(-1);
    public static final Integer ID_OPCAO_SEXO_FEMININO = new Integer(-2);

    /**
     * Obtém as categorias de talento disponíveis para consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterCategoriasTalento() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        List lstRetorno = new ArrayList();
        try
        {
            CategoriaTalento objCategoriaTalento = new CategoriaTalento();
            objCategoriaTalento.setIdentificador(ID_CATEGORIA_DADOS_PESSOAIS);
            objCategoriaTalento.setNome("Dados pessoais");
            lstRetorno.add(objCategoriaTalento);
            lstRetorno.addAll(objCategoriaTalentoDAO.obterDisponiveisParaConsulta());
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
     * Obtém uma categoria de talento de consulta a partir da chave
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
            if (ID_CATEGORIA_DADOS_PESSOAIS.toString().equals(strChave))
            {
                objCategoriaTalento = new CategoriaTalento();
                objCategoriaTalento.setIdentificador(ID_CATEGORIA_DADOS_PESSOAIS);
                objCategoriaTalento.setNome("Dados pessoais");
            }
            else
            {
                objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(strChave);
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
        return objCategoriaTalento;
    }

    /**
     * Obtém os relacionamentos categoria/atributo disponíveis para a consulta para a categoria informada
     *
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterCategoriaAtributosTalento(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstCategoriaAtributosTalento = null;
        try
        {
            CategoriaAtributoTalento objCategoriaAtributoTalento = null;
            AtributoTalento objAtributoTalento = null;
            TipoHTML objTipoHTML = null;
            if (objCategoriaTalento.getIdentificador().equals(ID_CATEGORIA_DADOS_PESSOAIS))
            {
                // Instancia lista
                lstCategoriaAtributosTalento = new ArrayList();

                // Adiciona nome
                objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                objAtributoTalento = new AtributoTalento();
                objTipoHTML = new TipoHTML();
                objCategoriaAtributoTalento.setIdentificador(ID_ATRIBUTO_NOME);
                objAtributoTalento.setIdentificador(ID_ATRIBUTO_NOME);
                objAtributoTalento.setNome("Nome");
                objAtributoTalento.setTipoDado("T");
                objTipoHTML.setDescricao("TEXT");
                objTipoHTML.setMultiplicidade("L");
                objAtributoTalento.setTipoHTML(objTipoHTML);
                objCategoriaAtributoTalento.setAtributoTalento(objAtributoTalento);
                objCategoriaAtributoTalento.setCategoriaTalento(objCategoriaTalento);
                lstCategoriaAtributosTalento.add(objCategoriaAtributoTalento);

                // Adiciona idade
                objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                objAtributoTalento = new AtributoTalento();
                objTipoHTML = new TipoHTML();
                objCategoriaAtributoTalento.setIdentificador(ID_ATRIBUTO_IDADE);
                objAtributoTalento.setIdentificador(ID_ATRIBUTO_IDADE);
                objAtributoTalento.setNome("Idade");
                objAtributoTalento.setTipoDado("N");
                objTipoHTML.setDescricao("TEXT");
                objTipoHTML.setMultiplicidade("L");
                objAtributoTalento.setTipoHTML(objTipoHTML);
                objCategoriaAtributoTalento.setAtributoTalento(objAtributoTalento);
                objCategoriaAtributoTalento.setCategoriaTalento(objCategoriaTalento);
                lstCategoriaAtributosTalento.add(objCategoriaAtributoTalento);

                // Adiciona sexo
                objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                objAtributoTalento = new AtributoTalento();
                objTipoHTML = new TipoHTML();
                objCategoriaAtributoTalento.setIdentificador(ID_ATRIBUTO_SEXO);
                objAtributoTalento.setIdentificador(ID_ATRIBUTO_SEXO);
                objAtributoTalento.setNome("Sexo");
                objAtributoTalento.setTipoDado("N");
                objTipoHTML.setDescricao("SELECT-ONE");
                objTipoHTML.setMultiplicidade("U");
                objAtributoTalento.setTipoHTML(objTipoHTML);
                objCategoriaAtributoTalento.setAtributoTalento(objAtributoTalento);
                objCategoriaAtributoTalento.setCategoriaTalento(objCategoriaTalento);
                lstCategoriaAtributosTalento.add(objCategoriaAtributoTalento);

            }
            else
            {
                lstCategoriaAtributosTalento = objCategoriaAtributoTalentoDAO.obterParaConsultaPorCategoriaTalento(objCategoriaTalento);
                objCategoriaAtributoTalentoDAO.inicializarCategoriaTalento(lstCategoriaAtributosTalento);
                objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(lstCategoriaAtributosTalento);
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
        return lstCategoriaAtributosTalento;
    }

    /**
     * Obtém as opções de determinado atributo de talento (desde que não tenha filhos) , se existirem
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterOpcoesPorAtributoTalento(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstAtributoTalentoOpcoes = null;
        try
        {
            AtributoTalentoOpcao objAtributoTalentoOpcao = null;
            if (objAtributoTalento.getIdentificador().equals(ID_ATRIBUTO_SEXO))
            {
                // Instancia lista
                lstAtributoTalentoOpcoes = new ArrayList();

                // Sexo masculino
                objAtributoTalentoOpcao = new AtributoTalentoOpcao();
                objAtributoTalentoOpcao.setIdentificador(ID_OPCAO_SEXO_MASCULINO);
                objAtributoTalentoOpcao.setDescricao("Masculino");
                lstAtributoTalentoOpcoes.add(objAtributoTalentoOpcao);

                // Sexo feminino
                objAtributoTalentoOpcao = new AtributoTalentoOpcao();
                objAtributoTalentoOpcao.setIdentificador(ID_OPCAO_SEXO_FEMININO);
                objAtributoTalentoOpcao.setDescricao("Feminino");
                lstAtributoTalentoOpcoes.add(objAtributoTalentoOpcao);

            }
            else
            {
                lstAtributoTalentoOpcoes = objAtributoTalentoOpcaoDAO.obterPorAtributoTalento(objAtributoTalento);
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
        return lstAtributoTalentoOpcoes;
    }

    /**
     * Verifica se determinado atributo de talento têm filhos na relação categoria/atributo
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejado
     * 
     * @return boolean Com a verificação
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public boolean verificarExistenciaCategoriaAtributosFilhos(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        boolean blnExistenciaFilhos = false;
        try
        {
            blnExistenciaFilhos = objCategoriaAtributoTalentoDAO.verificarExistenciaCategoriaAtributosTalentoFilhos(objCategoriaAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return blnExistenciaFilhos;
    }

    /**
     * Obtém uma categoria/atributo de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaAtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CategoriaAtributoTalento obterCategoriaAtributoTalentoPelaChave(String strChave) throws CDException
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
            CategoriaTalento objCategoriaTalento = null;
            AtributoTalento objAtributoTalento = null;
            TipoHTML objTipoHTML = null;
            if (ID_ATRIBUTO_NOME.toString().equals(strChave))
            {
                // Nome
                objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                objCategoriaTalento = new CategoriaTalento();
                objAtributoTalento = new AtributoTalento();
                objTipoHTML = new TipoHTML();
                objCategoriaTalento.setIdentificador(ID_CATEGORIA_DADOS_PESSOAIS);
                objCategoriaAtributoTalento.setIdentificador(ID_ATRIBUTO_NOME);
                objAtributoTalento.setIdentificador(ID_ATRIBUTO_NOME);
                objAtributoTalento.setNome("Nome");
                objAtributoTalento.setTipoDado("T");
                objTipoHTML.setDescricao("TEXT");
                objTipoHTML.setMultiplicidade("L");
                objAtributoTalento.setTipoHTML(objTipoHTML);
                objCategoriaAtributoTalento.setAtributoTalento(objAtributoTalento);
                objCategoriaAtributoTalento.setCategoriaTalento(objCategoriaTalento);
            }
            else if (ID_ATRIBUTO_IDADE.toString().equals(strChave))
            {
                // Idade
                objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                objCategoriaTalento = new CategoriaTalento();
                objAtributoTalento = new AtributoTalento();
                objTipoHTML = new TipoHTML();
                objCategoriaTalento.setIdentificador(ID_CATEGORIA_DADOS_PESSOAIS);
                objCategoriaAtributoTalento.setIdentificador(ID_ATRIBUTO_IDADE);
                objAtributoTalento.setIdentificador(ID_ATRIBUTO_IDADE);
                objAtributoTalento.setNome("Idade");
                objAtributoTalento.setTipoDado("N");
                objTipoHTML.setDescricao("TEXT");
                objTipoHTML.setMultiplicidade("L");
                objAtributoTalento.setTipoHTML(objTipoHTML);
                objCategoriaAtributoTalento.setAtributoTalento(objAtributoTalento);
                objCategoriaAtributoTalento.setCategoriaTalento(objCategoriaTalento);
            }
            else if (ID_ATRIBUTO_SEXO.toString().equals(strChave))
            {
                // Sexo
                objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                objCategoriaTalento = new CategoriaTalento();
                objAtributoTalento = new AtributoTalento();
                objTipoHTML = new TipoHTML();
                objCategoriaTalento.setIdentificador(ID_CATEGORIA_DADOS_PESSOAIS);
                objCategoriaAtributoTalento.setIdentificador(ID_ATRIBUTO_SEXO);
                objAtributoTalento.setIdentificador(ID_ATRIBUTO_SEXO);
                objAtributoTalento.setNome("Sexo");
                objAtributoTalento.setTipoDado("N");
                objTipoHTML.setDescricao("SELECT-ONE");
                objTipoHTML.setMultiplicidade("U");
                objAtributoTalento.setTipoHTML(objTipoHTML);
                objCategoriaAtributoTalento.setAtributoTalento(objAtributoTalento);
                objCategoriaAtributoTalento.setCategoriaTalento(objCategoriaTalento);
            }
            else
            {
                objCategoriaAtributoTalento = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPelaChave(strChave);
                objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(objCategoriaAtributoTalento);
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
        return objCategoriaAtributoTalento;
    }

    /**
     * Obtém a hierarquia inferior da categoria/atributo desejada
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterCategoriaAtributoTalentoHierarquiaInferior(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();
        List lstCategoriaAtributoTalentoHierarquiaInferior = new ArrayList();

        try
        {
            lstCategoriaAtributoTalentoHierarquiaInferior = objCategoriaAtributoTalentoFacade.obterHierarquiaInferior(objCategoriaAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }

        return lstCategoriaAtributoTalentoHierarquiaInferior;
    }

    /**
     * Obtém os relacionamentos categoria/atributo que são filhos da categoria/atributo informado
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterCategoriaAtributosTalentoFilhos(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstCategoriaAtributosTalentoFilhos = null;
        try
        {
            lstCategoriaAtributosTalentoFilhos = objCategoriaAtributoTalentoDAO.obterTodosOsFilhos(objCategoriaAtributoTalento);
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(lstCategoriaAtributosTalentoFilhos);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstCategoriaAtributosTalentoFilhos;
    }

    /**
     * Obtém as opções filhas de determinada opção pai
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterOpcoesPeloPaiEAtributoTalento(AtributoTalentoOpcao objAtributoTalentoOpcaoPai, AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstAtributoTalentoOpcoes = null;
        try
        {
            lstAtributoTalentoOpcoes = objAtributoTalentoOpcaoDAO.obterPeloPaiEAtributoTalento(objAtributoTalentoOpcaoPai, objAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributoTalentoOpcoes;
    }

    /**
     * Obtém uma opção de talento de consulta a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AtributoTalentoOpcao POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public AtributoTalentoOpcao obterAtributoTalentoOpcaoPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        AtributoTalentoOpcao objAtributoTalentoOpcao = null;
        try
        {
            if (ID_OPCAO_SEXO_MASCULINO.toString().equals(strChave))
            {
                objAtributoTalentoOpcao = new AtributoTalentoOpcao();
                objAtributoTalentoOpcao.setIdentificador(ID_OPCAO_SEXO_MASCULINO);
                objAtributoTalentoOpcao.setDescricao("Masculino");
            }
            else if (ID_OPCAO_SEXO_FEMININO.toString().equals(strChave))
            {
                objAtributoTalentoOpcao = new AtributoTalentoOpcao();
                objAtributoTalentoOpcao.setIdentificador(ID_OPCAO_SEXO_FEMININO);
                objAtributoTalentoOpcao.setDescricao("Feminino");
            }
            else
            {
                objAtributoTalentoOpcao = (AtributoTalentoOpcao) objAtributoTalentoOpcaoDAO.obterPelaChave(strChave);
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
        return objAtributoTalentoOpcao;
    }

    /**
     * Obtém um registro de pessoa a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Pessoa POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public Pessoa obterPessoaPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PessoaDAO objPessoaDAO = new PessoaDAO();
        Pessoa objPessoa = null;
        try
        {
            objPessoa = (Pessoa) objPessoaDAO.obterPelaChave(strChave);
            if (objPessoa != null && objPessoa.getGrupo() != null)
            {
                objPessoaDAO.inicializarGrupo(objPessoa);
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
        return objPessoa;
    }

    /**
     * Obtém o total de pessoas com talentos cadastrados
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalPessoasComTalentos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        TalentoDAO objTalentoDAO = new TalentoDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objTalentoDAO.obterTotalPessoasComTalentos();
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
     * Efetua consulta de talentos
     * 
     * @param mapConsulta Contendo os parâmetros de consulta
     * @param objPessoa Pessoa que está realizando a consulta
     * @param intMaximoPermitido Máximo de registros permitidos para o retorno da consulta
     * @param stbTotalRetornado Total de registros retornados
     * @param objSistema Sistema o qual deverão ser buscados os perfis para consulta
     * @param blnFiltroGrupo Para indicar se deverá ou não haver filtro por grupo
     * 
     * @return List Contendo o resultado da consulta, se o número máximo não for ultrapassado
     * 
     * @throws DAOException se ocorrer um erro relacionado com banco de dados 
     */

    public List consultar(Map mapConsultasTalento, UsuarioAutenticado objPessoa, int intMaximoPermitido, StringBuffer stbTotalRetornado, boolean blnFiltroGrupo) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        ConsultaTalentoDAO objConsultaTalentoDAO = new ConsultaTalentoDAO();
        List lstConsultaTalentos = null;
        try
        {
            lstConsultaTalentos = objConsultaTalentoDAO.consultar(mapConsultasTalento, objPessoa, intMaximoPermitido, stbTotalRetornado, blnFiltroGrupo);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstConsultaTalentos;
    }

}

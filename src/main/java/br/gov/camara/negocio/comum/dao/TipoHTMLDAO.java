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

package br.gov.camara.negocio.comum.dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.comum.pojo.TipoHTML;
import br.gov.camara.negocio.exception.DAOException;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.exception.DAOException;

/**
 * Classe DAO para a tabela TipoHTML
 */
public class TipoHTMLDAO extends DAO
{
    // Vari�veis de inst�ncia de configura��o
    private static Log log = LogFactory.getLog(TipoHTMLDAO.class);

    /**
     * Construtor sem argumentos
     */
    public TipoHTMLDAO()
    {
        super("TipoHTMLDAO");
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @throws SigespDAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta consulta
        String strConsulta = " FROM" + " TipoHTML tipoHTML" +

        " ORDER BY" + " tipoHTML.descricao ASC";

        // Recupera os dados
        return this.obter(strConsulta);

    }

    /**
     * Obt�m um registro a partir da chave
     * 
     * @param strChave Chave do registro a ser obtido
     * @return Object POJO representando o registro obtido
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public Object obterPelaChave(String strChave) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta consulta
        String strConsulta = " FROM" + " TipoHTML tipoHTML" +

        " WHERE" + " tipoHTML.identificador = " + strChave;

        // Recupera os dados
        return this.obter(strConsulta).get(0);
    }

    /**
     * Exclui um registro
     * 
     * @param TipoHTML POJO representando o objeto a ser exclu�do
     * @return String Contendo a consulta
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public String excluirImpl(Object objTipoHTML) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        throw new DAOException("M�todo n�o implementado");
    }

    /**
     * M�todo que pede a inicializa��o de um TipoHTML, que � lazy
     *
     * @param tipoHTML Classe que tem o atributo que vai ser recuperado
     *
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     *
     */
    public void inicializarTipoHTML(TipoHTML tipoHTML) throws DAOException
    {
        inicializarRelacionamento(tipoHTML);
    }

    /**
     *  M�todo que pede a inicializa��o dos TipoHTML, que � lazy
     * 
     * @param colTipoHTML  Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarTipoHTML(Collection colTipoHTML) throws DAOException
    {
        Iterator itrTipoHTML = colTipoHTML.iterator();
        while (itrTipoHTML.hasNext())
        {
            inicializarTipoHTML(((TipoHTML) itrTipoHTML.next()));
        }
    }

}

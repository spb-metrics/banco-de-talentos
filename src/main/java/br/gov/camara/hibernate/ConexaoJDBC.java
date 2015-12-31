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

package br.gov.camara.hibernate;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;

import br.gov.camara.hibernate.exception.HibernateExceptionCD;
import br.gov.camara.negocio.ParametroDAO;

class ConexaoJDBC
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(ConexaoJDBC.class);

    private Session sessao = null;

    ConexaoJDBC(Session sessao)
    {
        try
        {
            this.sessao = sessao;
        }
        catch (Exception e)
        {
            log.error("Ocorreu um erro recuperando a sess�o hibernate: " + e.getMessage(), e);
        }
    }

    static void resolverFaltaAutocommit(String nomeConexao, Session sessaoHibernate)
    {
        try
        {
            Connection conexao = ((SessionImpl) sessaoHibernate).getJDBCContext().connection();

            if (!conexao.isClosed() && !conexao.getAutoCommit())
            {
                conexao.commit();
            }
        }
        catch (Throwable sqle)
        {
            throw new HibernateExceptionCD(sqle);
        }
    }

    int executar(String comandoSql)
    {
        return executarComParametros(comandoSql, null);
    }

    int executarComParametros(String comandoSql, ParametroDAO parametros)
    {
        Query querySql = criarQuerySQL(comandoSql);

        if (parametros != null)
        {
            querySql = parametros.setarParametroQuery(querySql);
        }

        return querySql.executeUpdate();
    }

    List recuperar(String comandoSql)
    {
        return recuperarComParametros(comandoSql, null);
    }

    List recuperarComParametros(String comandoSql, ParametroDAO parametros)
    {
        Query querySql = criarQuerySQL(comandoSql);

        if (parametros != null)
        {
            querySql = parametros.setarParametroQuery(querySql);
        }

        return querySql.list();
    }

    private Query criarQuerySQL(String comandoSql)
    {
        Query querySql = sessao.createSQLQuery(comandoSql);
        querySql.setResultTransformer(AliasLowerToMapResultTransformer.INSTANCE);
        return querySql;
    }
}

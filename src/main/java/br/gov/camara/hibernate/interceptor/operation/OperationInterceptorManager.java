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

package br.gov.camara.hibernate.interceptor.operation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.exception.CDException;
import br.gov.camara.hibernate.exception.HibernateExceptionCD;

public class OperationInterceptorManager extends EmptyInterceptor
{

    /**
     * 
     */
    private static final long serialVersionUID = -6202885132279848371L;

    public static final String TIPO_INTERCEPTOR_OPERATION = "operation";

    private List lstInterceptorOperation = null;
    private int quantidadeInterceptorsOperation = 0;

    public OperationInterceptorManager(List lstNomesInterceptadoresOperacao)
    {
        if (lstNomesInterceptadoresOperacao == null || lstNomesInterceptadoresOperacao.size() < 1)
        {
            throw new RuntimeException("A lista de interceptadores de opera��o n�o pode ser vazia");
        }

        this.lstInterceptorOperation = new ArrayList();
        ClasseDinamica classeDinamica = new ClasseDinamica();
        for (int i = 0; i < lstNomesInterceptadoresOperacao.size(); i++)
        {
            OperationInterceptor operationInterceptor = null;

            try
            {
                operationInterceptor = (OperationInterceptor) classeDinamica.obterInstancia((String) lstNomesInterceptadoresOperacao.get(i));
            }
            catch (CDException cde)
            {
                throw new RuntimeException("Ocorreu o seguinte erro carregando o interceptador de opera��o '"
                        + (String) lstNomesInterceptadoresOperacao.get(i)
                        + "': "
                        + cde.getMessage());
            }

            this.lstInterceptorOperation.add(operationInterceptor);
        }

        this.quantidadeInterceptorsOperation = this.lstInterceptorOperation.size();
    }

    // SELECT
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException
    {
        boolean houveAlteracao = false;
        for (int i = 0; i < this.quantidadeInterceptorsOperation; i++)
        {
            OperationInterceptor operationInterceptor = (OperationInterceptor) this.lstInterceptorOperation.get(i);
            try
            {
                if (operationInterceptor.executarAntesCarregar(entity, id, state, propertyNames, types))
                {
                    houveAlteracao = true;
                }
            }
            catch (HibernateExceptionCD hecd)
            {
                throw new CallbackException(hecd);
            }
        }
        return houveAlteracao;
    }

    // UPDATE
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException
    {
        boolean houveAlteracao = false;
        for (int i = 0; i < this.quantidadeInterceptorsOperation; i++)
        {
            OperationInterceptor operationInterceptor = (OperationInterceptor) this.lstInterceptorOperation.get(i);
            try
            {
                if (operationInterceptor.executarAntesAlterar(entity, id, currentState, previousState, propertyNames, types))
                {
                    houveAlteracao = true;
                }
            }
            catch (HibernateExceptionCD hecd)
            {
                throw new CallbackException(hecd);
            }
        }
        return houveAlteracao;
    }

    // INSERT
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException
    {
        boolean houveAlteracao = false;
        for (int i = 0; i < this.quantidadeInterceptorsOperation; i++)
        {
            OperationInterceptor operationInterceptor = (OperationInterceptor) this.lstInterceptorOperation.get(i);
            try
            {
                if (operationInterceptor.executarAntesSalvar(entity, id, state, propertyNames, types))
                {
                    houveAlteracao = true;
                }
            }
            catch (HibernateExceptionCD hecd)
            {
                throw new CallbackException(hecd);
            }
        }
        return houveAlteracao;
    }

    // DELETE
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException
    {
        for (int i = 0; i < this.quantidadeInterceptorsOperation; i++)
        {
            OperationInterceptor operationInterceptor = (OperationInterceptor) this.lstInterceptorOperation.get(i);
            try
            {
                operationInterceptor.executarAntesExcluir(entity, id, state, propertyNames, types);
            }
            catch (HibernateExceptionCD hecd)
            {
                throw new CallbackException(hecd);
            }
        }
    }

    public void postFlush(Iterator iterator) throws CallbackException
    {
        for (int i = 0; i < this.quantidadeInterceptorsOperation; i++)
        {
            OperationInterceptor operationInterceptor = (OperationInterceptor) this.lstInterceptorOperation.get(i);
            try
            {
                operationInterceptor.executarAposEfetivarOperacao(iterator);
            }
            catch (HibernateExceptionCD hecd)
            {
                throw new CallbackException(hecd);
            }
        }
    }

    public void preFlush(Iterator iterator) throws CallbackException
    {
        for (int i = 0; i < this.quantidadeInterceptorsOperation; i++)
        {
            OperationInterceptor operationInterceptor = (OperationInterceptor) this.lstInterceptorOperation.get(i);
            try
            {
                operationInterceptor.executarAntesEfetivarOperacao(iterator);
            }
            catch (HibernateExceptionCD hecd)
            {
                throw new CallbackException(hecd);
            }
        }
    }
}

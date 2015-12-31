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

package br.gov.camara.negocio.bancotalentos.util;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.exception.DAOException;

public class BancoTalentosUtil
{
    private static final String CHAVE_FUNCAO_BD_CONVERTER_STRING_EM_DATA = "funcaoBDConverterStringEmData";
    private static final String CHAVE_FUNCAO_BD_REMOVER_HORAS_DE_DATA = "funcaoBDRemoverHorasDeData";

    public static String utilizarFuncaoBDConversaoStringEmData(DAO objDAOOrigem, String strStringEntreAspasOuNomeCampo) throws DAOException
    {
        String funcaoBDConverterStringEmData = objDAOOrigem.obterPropriedadeSubstituicao(CHAVE_FUNCAO_BD_CONVERTER_STRING_EM_DATA);
        if (funcaoBDConverterStringEmData == null || "".equals(funcaoBDConverterStringEmData))
        {
            throw new DAOException("A chave '" + CHAVE_FUNCAO_BD_CONVERTER_STRING_EM_DATA + "' n�o foi configurada no arquivo 'hibernate-config.xml'");
        }
        funcaoBDConverterStringEmData = objDAOOrigem.realizarSubstituicao(funcaoBDConverterStringEmData, strStringEntreAspasOuNomeCampo);

        return funcaoBDConverterStringEmData;
    }

    public static String utilizarFuncaoBDRemocaoHorasDeData(DAO objDAOOrigem, String strStringEntreAspasOuNomeCampo) throws DAOException
    {
        String funcaoBDRemoverHorasDeData = objDAOOrigem.obterPropriedadeSubstituicao(CHAVE_FUNCAO_BD_REMOVER_HORAS_DE_DATA);
        funcaoBDRemoverHorasDeData = objDAOOrigem.realizarSubstituicao(funcaoBDRemoverHorasDeData, strStringEntreAspasOuNomeCampo);
        if (funcaoBDRemoverHorasDeData == null || "".equals(funcaoBDRemoverHorasDeData))
        {
            throw new DAOException("A chave '" + CHAVE_FUNCAO_BD_REMOVER_HORAS_DE_DATA + "' n�o foi configurada no arquivo 'hibernate-config.xml'");
        }

        return funcaoBDRemoverHorasDeData;
    }
}

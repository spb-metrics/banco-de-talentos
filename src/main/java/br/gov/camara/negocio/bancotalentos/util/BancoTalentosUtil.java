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
            throw new DAOException("A chave '" + CHAVE_FUNCAO_BD_CONVERTER_STRING_EM_DATA + "' não foi configurada no arquivo 'hibernate-config.xml'");
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
            throw new DAOException("A chave '" + CHAVE_FUNCAO_BD_REMOVER_HORAS_DE_DATA + "' não foi configurada no arquivo 'hibernate-config.xml'");
        }

        return funcaoBDRemoverHorasDeData;
    }
}

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

package br.gov.camara.negocio.comum.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory para a criação de beans genéricos
 */
public class FabricaBeans
{
    // Variáveis de instância
    public static final int PREENCHIMENTO = 0;
    public static final int OPERADOR_RELACIONAL = 1;
    public static final int DIRECAO_EVENTO = 2;
    public static final int TIPO_DADO = 3;
    
    /*
     * Cria o bean genérico de acordo com a especificação desejada
     * 
     * @param int Indica o tipo de bean (verificar constantes da classe)]
     * 
     * @return List com os beans genéricos especificados
     */
    public static List criar(int intTipoBean)
    {
        switch (intTipoBean)
        {
            case PREENCHIMENTO:
                return criarPreenchimento(); 
                
            case OPERADOR_RELACIONAL:
                return criarOperadorRelacional();

            case DIRECAO_EVENTO:
                return criarDirecaoEvento();
 
            case TIPO_DADO:
                return criarTipoDado();
 
            default:
                throw new RuntimeException("Tipo de bean não identificado");
        }
    }
    
    private static List criarPreenchimento()
    {
        // Cria lista
        List lstBeans = new ArrayList();
        Bean beaBean = null;
        
        // Adiciona possíveis valores
        beaBean = new Bean();
        beaBean.setIdentificador("R");
        beaBean.setDescricao("Requerido");
        lstBeans.add(beaBean);
                         
        beaBean = new Bean();
        beaBean.setIdentificador("O");
        beaBean.setDescricao("Opcional");
        lstBeans.add(beaBean);
                         
        beaBean = new Bean();
        beaBean.setIdentificador("N");
        beaBean.setDescricao("Nulo");
        lstBeans.add(beaBean);                 
        
        beaBean = new Bean();
        beaBean.setIdentificador("S");
        beaBean.setDescricao("Sequencial");
        lstBeans.add(beaBean);
        
        // Retorno
        return lstBeans;                         
    }

    private static List criarOperadorRelacional()
    {
        // Cria lista
        List lstBeans = new ArrayList();
        Bean beaBean = null;
        
        // Adiciona possíveis valores
        beaBean = new Bean();
        beaBean.setIdentificador("==");
        beaBean.setDescricao("==");
        lstBeans.add(beaBean);
                         
        beaBean = new Bean();
        beaBean.setIdentificador("!=");
        beaBean.setDescricao("!=");
        lstBeans.add(beaBean);

        beaBean = new Bean();
        beaBean.setIdentificador(">=");
        beaBean.setDescricao(">=");
        lstBeans.add(beaBean);

        beaBean = new Bean();
        beaBean.setIdentificador("<=");
        beaBean.setDescricao("<=");
        lstBeans.add(beaBean);

        beaBean = new Bean();
        beaBean.setIdentificador(">");
        beaBean.setDescricao(">");
        lstBeans.add(beaBean);

        beaBean = new Bean();
        beaBean.setIdentificador("<");
        beaBean.setDescricao("<");
        lstBeans.add(beaBean);
        
        // Retorno
        return lstBeans;                         
    }

    private static List criarDirecaoEvento()
    {
        // Cria lista
        List lstBeans = new ArrayList();
        Bean beaBean = null;
        
        // Adiciona possíveis valores
        beaBean = new Bean();
        beaBean.setIdentificador("S");
        beaBean.setDescricao("Servidor=>Vaga");
        lstBeans.add(beaBean);
                         
        beaBean = new Bean();
        beaBean.setIdentificador("V");
        beaBean.setDescricao("Vaga=>Servidor");
        lstBeans.add(beaBean);

        // Retorno
        return lstBeans;                         
    }    

    private static List criarTipoDado()
    {
        // Cria lista
        List lstBeans = new ArrayList();
        Bean beaBean = null;
        
        // Adiciona possíveis valores
        beaBean = new Bean();
        beaBean.setIdentificador("T");
        beaBean.setDescricao("Texto");
        lstBeans.add(beaBean);
                         
        beaBean = new Bean();
        beaBean.setIdentificador("N");
        beaBean.setDescricao("Número");
        lstBeans.add(beaBean);

        beaBean = new Bean();
        beaBean.setIdentificador("D");
        beaBean.setDescricao("Data");
        lstBeans.add(beaBean);

        // Retorno
        return lstBeans;                         
    }
}

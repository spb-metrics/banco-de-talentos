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

package br.gov.camara.negocio.comum.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory para a cria��o de beans gen�ricos
 */
public class FabricaBeans
{
    // Vari�veis de inst�ncia
    public static final int PREENCHIMENTO = 0;
    public static final int OPERADOR_RELACIONAL = 1;
    public static final int DIRECAO_EVENTO = 2;
    public static final int TIPO_DADO = 3;
    
    /*
     * Cria o bean gen�rico de acordo com a especifica��o desejada
     * 
     * @param int Indica o tipo de bean (verificar constantes da classe)]
     * 
     * @return List com os beans gen�ricos especificados
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
                throw new RuntimeException("Tipo de bean n�o identificado");
        }
    }
    
    private static List criarPreenchimento()
    {
        // Cria lista
        List lstBeans = new ArrayList();
        Bean beaBean = null;
        
        // Adiciona poss�veis valores
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
        
        // Adiciona poss�veis valores
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
        
        // Adiciona poss�veis valores
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
        
        // Adiciona poss�veis valores
        beaBean = new Bean();
        beaBean.setIdentificador("T");
        beaBean.setDescricao("Texto");
        lstBeans.add(beaBean);
                         
        beaBean = new Bean();
        beaBean.setIdentificador("N");
        beaBean.setDescricao("N�mero");
        lstBeans.add(beaBean);

        beaBean = new Bean();
        beaBean.setIdentificador("D");
        beaBean.setDescricao("Data");
        lstBeans.add(beaBean);

        // Retorno
        return lstBeans;                         
    }
}

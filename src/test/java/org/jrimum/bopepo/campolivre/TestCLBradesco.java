/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:14:15
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:14:15
 * 
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.excludes.CampoLivreBaseTest;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste unitário do campo livre do banco bradesco.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBradesco extends CampoLivreBaseTest {

	private Titulo titulo;

	@Before
	public void setUp() throws Exception {

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.BANCO_BRADESCO.create());

		Agencia agencia = new Agencia(1234, "1");
		contaBancaria.setAgencia(agencia);

		contaBancaria.setCarteira(new Carteira(5));

		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		contaBancaria.setNumeroDaConta(numeroDaConta);

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("12345678901");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		setClasseGeradoraDoCampoLivre(CLBradesco.class);
		setCampoLivreValidoAsString("1234051234567890100067890");
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteAgenciaNull() {

		titulo.getContaBancaria().setAgencia(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraNull() {

		titulo.getContaBancaria().setCarteira(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoNegativo() {

		titulo.getContaBancaria().setCarteira(new Carteira(-1));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoAcimaDe2Digitos() {

		titulo.getContaBancaria().setCarteira(new Carteira(111));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroNull() {

		titulo.setNossoNumero(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComBrancos() {

		titulo.setNossoNumero("           ");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComEspacos() {

		titulo.setNossoNumero("01234 56789");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComTamanhoDiferenteDe11() {

		titulo.setNossoNumero("0123456789");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaNull() {

		titulo.getContaBancaria().setNumeroDaConta(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaComCodigoNegativo() {

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(-1));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermitNumeroDaContaComCodigoAcimaDe7Digitos() {

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(12345678));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
}
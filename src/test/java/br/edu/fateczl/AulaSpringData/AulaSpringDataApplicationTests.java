package br.edu.fateczl.AulaSpringData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.fateczl.AulaSpringData.model.Depto;
import br.edu.fateczl.AulaSpringData.model.Empregado;
import br.edu.fateczl.AulaSpringData.model.EmpregadoProjeto;
import br.edu.fateczl.AulaSpringData.model.EmpregadoProjetoId;
import br.edu.fateczl.AulaSpringData.model.Projeto;
import br.edu.fateczl.AulaSpringData.repository.DeptoRepository;
import br.edu.fateczl.AulaSpringData.repository.EmpregadoProjetoRepository;
import br.edu.fateczl.AulaSpringData.repository.EmpregadoRepository;
import br.edu.fateczl.AulaSpringData.repository.ProjetoRepository;

@SpringBootTest
class AulaSpringDataApplicationTests {
	
	@Autowired
	private DeptoRepository dRep;

	@Autowired
	private EmpregadoRepository eRep;
	
	@Autowired
	private ProjetoRepository pRep;
	
	@Autowired
	private EmpregadoProjetoRepository epRep;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void crudDepto() {
		Depto d1 = new Depto(101, "Desenvolvimento Java", null);
		Depto d2 = new Depto(102, "Desenvolvimento C", null);
		Depto d3 = new Depto(103, "Dev. Python", null);
		
		
		dRep.save(d1);
		dRep.save(d2);	
		dRep.save(d3);
		
		d2.setNome("Dev. C++");
		dRep.save(d2);
		
		List<Depto> deptos = dRep.findAll();
		for (Depto dpt : deptos) {
			System.out.println(dpt.toString());
		}
		System.out.println("===========================================");
		Depto depto = dRep.findById(d1.getCodigo()).get();
		System.out.println(depto.toString());
		
		dRep.delete(d3);
	}

	@Test
	void crudEmpregado() {
		Empregado e1 = new Empregado(100001, "Fulano de Taç", "M", "1122223333", 
				LocalDate.of(2023, 1, 1), null, "Dev. Jr.", 
				new BigDecimal(4500.00), new Depto(101, null, null));
		eRep.save(e1);
		
		Empregado emp = eRep.findById(e1.getMatricula()).get();
		Depto d = dRep.findById(emp.getDepto().getCodigo()).get();
		emp.setDepto(d);

		System.out.print(emp.getMatricula() + " - ");
		System.out.print(emp.getNome() + " - ");
		System.out.print(emp.getSexo() + " - ");
		System.out.print(emp.getDataAdmissao() + " - ");
		System.out.print(emp.getDtAdmissao() + " - ");
		System.out.print(emp.getCargo() + " - ");
		System.out.print(emp.getSalario() + " - ");
		System.out.print(emp.getDepto().getCodigo() + " - ");
		System.out.println(emp.getDepto().getNome());
	}
	
	@Test
	void EmpProjCrud() {
		Projeto p = new Projeto(1001, "Desenvolvimento Módulo Financeiro", 70);
		pRep.save(p);
		
		Empregado e = eRep.findById(100001).get();
		Depto d = dRep.findById(e.getDepto().getCodigo()).get();
		e.setDepto(d);
		EmpregadoProjeto ep = new EmpregadoProjeto();
		ep.setEmpregado(e);
		ep.setProjeto(p);
		ep.setDataAlocacao(LocalDate.of(2024, 6, 10));
		
		epRep.save(ep);
		EmpregadoProjetoId epId = new EmpregadoProjetoId();
		epId.setEmpregado(e);
		epId.setProjeto(p);
		
		EmpregadoProjeto empProj = epRep.findById(epId).get();
		System.out.print(empProj.getEmpregado().getNome() + " - ");
		System.out.print(empProj.getProjeto().getNome() + " - ");
		System.out.print(empProj.getDataAlocacao().toString() + " - ");
		System.out.println(empProj.getTempoEmpregadoProjeto() + " dias");

	}
		
	
}

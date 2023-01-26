package com.smarteducation.smarteducation.model.CRUD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smarteducation.smarteducation.model.Repository.AlunosRepository;
import com.smarteducation.smarteducation.model.Repository.CursoRepository;
import com.smarteducation.smarteducation.model.Repository.MateriaRepository;
import com.smarteducation.smarteducation.model.Repository.StatusRepository;
import com.smarteducation.smarteducation.model.Schema.Alunos;
import com.smarteducation.smarteducation.model.Schema.Curso;
import com.smarteducation.smarteducation.model.Schema.Grade;
import com.smarteducation.smarteducation.model.Schema.Materias;
import com.smarteducation.smarteducation.model.Schema.Status;



@Service
public class AlunosService {
    @Autowired 
    AlunosRepository alunosRepository;
    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    StatusRepository statusRepository;

    public Materias materiaById(String id){
        return this.materiaRepository.findByid(id);
    }

    public Alunos buscarAlunoId(String id){
        return this.alunosRepository.findByid(id);
    }
    public void salvarAluno(Alunos aluno){
        this.alunosRepository.save(aluno);
    }


    public List<Materias> materiasCurso(String idAluno){
        Alunos aluno = this.alunosRepository.findByid(idAluno);
        Curso cursoAluno = this.cursoRepository.findBynome(aluno.getCurso().getNome());
        List<String> mateStrings = new ArrayList<>(cursoAluno.getEstruturaCurricular());
        List<Materias> materias = new ArrayList<>();
        for(int i = 0; i < mateStrings.size(); i++){
            Materias materia = this.materiaRepository.findBynome(mateStrings.get(i));
            materias.add(materia);
        }
        return materias;
    }
    
    public List<Materias> materiasDisponiveis(String idAluno){
        Alunos aluno = this.alunosRepository.findByid(idAluno);
        List<Materias> materiasCurso = this.materiasCurso(aluno.getId());
        // lista de materias que o aluno ja cursou
        List<Materias> disponiveis = new ArrayList<>(); // lista final para retornar
        List<Status> historico = new ArrayList<>(aluno.getHistorico());
        List<Materias> historicoFiltrado = new ArrayList<>(); // vai receber as materias do historico em que houve aprovação e que está sendo cursado
        List<String> historicoFiltradoNo = new ArrayList<>(); // vai receber as materias do historico em que houve aprovação e que está sendo cursado

        if(historico.size() == 0 || historico.isEmpty()){
            for (int k = materiasCurso.size() - 1; k >= 0; k = k-1){
                if (materiasCurso.get(k).getRequisito() == null || materiasCurso.get(k).getRequisito().isEmpty()){
                } else {
                    materiasCurso.remove(k);
                }
            }
        }

        for(int i = 0; i < historico.size(); i++){
            if(historico.get(i).getStatus() == 3){
                historicoFiltrado.add(historico.get(i).getMaterias());
            } else if(historico.get(i).getStatus() == 2){
                historicoFiltradoNo.add(historico.get(i).getMaterias().getNome());
            }
        }
        
        List<Integer> remover = new ArrayList<>(); // armazenar o índice das matérias que serão removidas
        for(int j = 0; j < materiasCurso.size(); j++){
            for (int l = 0; l < historico.size(); l++){
                if(materiasCurso.get(j).getNome().equals(historico.get(l).getMaterias().getNome()) && (historico.get(l).getStatus() == 2 || historico.get(l).getStatus() == 1)){
                    remover.add(j);
                }
                if(materiasCurso.get(j).getRequisito() != null){
                    if(!historicoFiltradoNo.containsAll(materiasCurso.get(j).getRequisito())){
                        remover.add(j);
                    }
                }
            }
        }


        remover = remover.stream().distinct().collect(Collectors.toList());
        Collections.sort(remover, (a, b) -> b - a);
        remover.forEach(item -> materiasCurso.remove(item.intValue()));
        
        disponiveis = new ArrayList<>(materiasCurso);

        return disponiveis;

    }

    public List<Status> verMatriculasAtuais(String id){
        Alunos aluno = this.alunosRepository.findByid(id);
        List<Status> materiasNF = new ArrayList<>();
        aluno.getHistorico().stream().forEach(e -> {
            if (e.getStatus() == 1){
                materiasNF.add(e);
            }
        });
        return materiasNF;
    }

    public List<Grade> verificaGrade(String idAluno){
        List<Grade> todosHorarios = new ArrayList<>();
        Alunos aluno = this.alunosRepository.findByid(idAluno);
        for(int i = 0; i < aluno.getHistorico().size(); i++){
            if(aluno.getHistorico().get(i).getStatus() == 1){
                aluno.getHistorico().get(i).getMaterias().getGrade().forEach(dia -> {
                    todosHorarios.add(dia);
                });
            }
        }
        return todosHorarios;
    }

    public void realizarMatricula(String id, String idAluno){
        List<Grade> verificaHorario = this.verificaGrade(idAluno);
        Materias materia = this.materiaById(id);
        Alunos aluno = this.buscarAlunoId(idAluno);
        Alunos alunoL = new Alunos();
        //construindo alunoL para guardar na lista com as informações necessárias
        alunoL.setNome(aluno.getNome());
        alunoL.setMatricula(aluno.getMatricula());
        alunoL.setEmail(aluno.getEmail());
        alunoL.setCurso(aluno.getCurso());
        List<Integer> k = new ArrayList<>();
        materia.getGrade().forEach(horario -> {
            for(int i = 0; i < verificaHorario.size(); i++){
                if(horario.getDia().equals(verificaHorario.get(i).getDia()) && horario.getHorario().equals(verificaHorario.get(i).getHorario())){
                    k.add(1);
                }
            }
        });

        if(k.size() == 0){
            Status materiaAtual = new Status();
            materiaAtual.setMaterias(materia);
            materiaAtual.setStatus(1);
            Materias materiaAtt = this.materiaRepository.findByid(materiaAtual.getMaterias().getId());
            materiaAtt.addAluno(alunoL);
            this.materiaRepository.save(materiaAtt);
            aluno.addHistorico(materiaAtual);
            this.salvarAluno(aluno);
        } else if(verificaHorario.isEmpty()){
            Status materiaAtual = new Status();
            materiaAtual.setMaterias(materia);
            materiaAtual.setStatus(1);
            Materias materiaAtt = this.materiaRepository.findByid(materiaAtual.getMaterias().getId());
            materiaAtt.addAluno(alunoL);
            this.materiaRepository.save(materiaAtt);
            aluno.addHistorico(materiaAtual);
            this.salvarAluno(aluno);
        } 
        
    }
    
    public List<Grade> listaHorarios(String idAluno){
        Alunos aluno = this.alunosRepository.findByid(idAluno);
        List<Grade> grade = new ArrayList<>();
        aluno.getHistorico().forEach(materia -> {
            if(materia.getStatus() == 1){
                materia.getMaterias().getGrade().forEach(horario -> {
                    grade.add(horario);
                });
            }
        });
        return grade;
    }

    public int chObrigatoria(String idAluno){
        Alunos aluno = this.alunosRepository.findByid(idAluno);
        List<Materias> materiasCurso = this.materiasCurso(aluno.getId());
        int ch = 0;
        for(int i = 0; i < materiasCurso.size(); i++){
            ch += materiasCurso.get(i).getCredito();
        }
        return ch*30;
    }

    public int chPendente(String idAluno){
        Alunos aluno = this.alunosRepository.findByid(idAluno);
        int ch = 0;
        List<Status> historico = aluno.getHistorico();
        for(int i = 0; i < historico.size(); i++){
            if(historico.get(i).getStatus() == 2){
                ch += historico.get(i).getMaterias().getCredito();
            }
        }
        return ch*30;
    }


}

package br.com.vida_pregressa.controllers;

import br.com.vida_pregressa.model.entities.Candidato;
import br.com.vida_pregressa.model.entities.Concurso;
import br.com.vida_pregressa.model.entities.Endereco;
import br.com.vida_pregressa.service.ConcursoService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.GDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class UploadArquivosController {

    @Autowired
    private ConcursoService concursoService; // Injete o serviço de concurso

    @PostMapping("/upload")
    public Concurso uploadFile(@RequestParam("id") Integer idConcurso, @RequestParam("file") MultipartFile file) {
        try {

            //recuperar o concurso por id
            Concurso concurso = concursoService.obterConcursoPorId(idConcurso).orElseThrow(() -> new RuntimeException("Concurso não encontrado"));
            //montar uma nova lista de candidatos
            ArrayList<Candidato> novaLista = new ArrayList<Candidato>();

            // Verifica se o arquivo é um XLSX
            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                throw new RuntimeException("O arquivo não é do tipo .xlsx");
            }

            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // supondo que estamos interessados na primeira aba

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Candidato candidato = new Candidato();
                Endereco endereco = new Endereco();
                Optional<Candidato> candidatoExistente = null;

                if (rowIndex != 0) {

                    Row row = sheet.getRow(rowIndex);
                    Cell cellNome = row.getCell(0);
                    Cell cellCPF = row.getCell(1);
                    Cell cellNomeMae = row.getCell(12);

                    if (cellNome == null) {
                        break;
                    }

                    //endereco
                    Cell cellLogradouro = row.getCell(2);
                    Cell cellNumero = row.getCell(3);
                    Cell cellComplemento = row.getCell(4);
                    Cell cellBairro = row.getCell(5);
                    Cell cellCidade = row.getCell(6);
                    Cell cellUF = row.getCell(7);
                    Cell cellDataInicio = row.getCell(8);
                    Cell cellDataTermino = row.getCell(9);
                    Cell cellClassificacao = row.getCell(11);

                    if (cellNomeMae.getCellType().equals(CellType.STRING)) {
                        candidato.setNomeMae(cellNomeMae.getStringCellValue());
                    }

                    if (cellNome.getCellType().equals(CellType.STRING)) {
                        candidato.setNome(cellNome.getStringCellValue());
                    }

                    if (cellCPF.getCellType().equals(CellType.STRING)) {

                        candidato.setCpf(cellCPF.getStringCellValue());

                        candidatoExistente = novaLista.stream()
                                .filter(c -> c.getCpf().equals(candidato.getCpf()))
                                .findFirst();
                    }

                    if (cellLogradouro.getCellType().equals(CellType.STRING)) {
                        endereco.setLogradouro(cellLogradouro.getStringCellValue());
                    }

                    if (cellNumero.getCellType().equals(CellType.STRING)) {
                        endereco.setNumero((cellNumero.getStringCellValue()));
                    }

                    if (cellComplemento.getCellType().equals(CellType.STRING)) {
                        endereco.setComplemento((cellComplemento.getStringCellValue()));
                    }

                    if (cellBairro.getCellType().equals(CellType.STRING)) {
                        endereco.setBairro((cellBairro.getStringCellValue()));
                    }

                    if (cellCidade.getCellType().equals(CellType.STRING)) {
                        endereco.setCidade((cellCidade.getStringCellValue()));
                    }


                    if (cellUF.getCellType().equals(CellType.STRING)) {
                        endereco.setUf((cellUF.getStringCellValue()));
                    }


                    if (cellComplemento.getCellType().equals(CellType.STRING)) {
                        endereco.setComplemento((cellComplemento.getStringCellValue()));
                    }

                    if (cellDataInicio.getCellType().equals(CellType.STRING)) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                        endereco.setDataInicioMoradia(formatter.parse(cellDataInicio.getStringCellValue()));
                    }

                    if (cellDataTermino != null && cellDataTermino.getCellType().equals(CellType.STRING)) {
                        if ( ! cellDataTermino.getStringCellValue().equals("")) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            endereco.setDataFimMoradia(formatter.parse( cellDataTermino.getStringCellValue()));
                        }
                    }

                    if (cellClassificacao.getCellType().equals(CellType.NUMERIC)) {
                        candidato.setClassificacao((int) cellClassificacao.getNumericCellValue());
                    }

                    if (candidatoExistente.isPresent()) {
                        endereco.setCandidato(candidatoExistente.get());
                        candidatoExistente.get().getEnderecos().add(endereco);
                    } else {
                        ArrayList<Endereco> listaEnderecos = new ArrayList<>();
                        listaEnderecos.add(endereco);

                        endereco.setCandidato(candidato);
                        candidato.setEnderecos(listaEnderecos);
                        candidato.setConcurso(concurso);
                        novaLista.add(candidato);
                    }
                }

            }
            workbook.close();

            //seta a nova lista de candidatos dentro do concurso
            concurso.setCandidatos(novaLista);
            concurso.setDataAlteracao(new Date());

            //atualiza concurso recuperado do banco
            concursoService.atualizarConcurso(concurso);
            return concurso;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao tentar fazer importação dos candidados dentro do concurso " + idConcurso);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao formatar data", e);
        }
    }
}

package br.com.mvp.cadastroclienteapi.controller;

import br.com.mvp.cadastroclienteapi.controller.request.AtualizaClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.request.CadastraClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.request.ConsultaClienteRequest;
import br.com.mvp.cadastroclienteapi.controller.response.AtualizaClienteResponse;
import br.com.mvp.cadastroclienteapi.controller.response.CadastraClienteResponse;
import br.com.mvp.cadastroclienteapi.controller.response.ConsultaClienteResponse;
import br.com.mvp.cadastroclienteapi.service.ClienteService;
import br.com.mvp.cadastroclienteapi.service.handler.ExceptionCliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {
    @Resource
    private MockMvc mockMvc;
    @MockBean
    private ClienteService service;
    @Resource
    private ObjectMapper mapper;

    @Test
    void incluirClienteSucesso() {
        var request = new CadastraClienteRequest("11111111111", LocalDate.now(), "11111111111", null);
        var jsonRequest = assertDoesNotThrow(() -> this.mapper.writeValueAsString(request));
        var response = new CadastraClienteResponse(UUID.randomUUID().toString(), "11111111111", LocalDate.now(), "11111111111", null);
        when(this.service.salvar(any(CadastraClienteRequest.class))).thenReturn(response);
        var jsonResponse = assertDoesNotThrow(() -> this.mapper.writeValueAsString(response));

        assertDoesNotThrow(() ->
                this.mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(content().string(jsonResponse))
        );
    }

    @Test
    void incluirClienteCPFExistente() {
        var request = new CadastraClienteRequest("11111111111", LocalDate.now(), "11111111111", null);
        var jsonRequest = assertDoesNotThrow(() -> this.mapper.writeValueAsString(request));
        when(this.service.salvar(any(CadastraClienteRequest.class))).thenThrow(new ExceptionCliente());

        assertDoesNotThrow(() ->
                this.mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andDo(print())
                        .andExpect(status().isUnprocessableEntity())
        );
    }

    @Test
    void incluirClienteSemBody() {
        assertDoesNotThrow(() ->
                this.mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
        );
    }

    @Test
    void atualizarClienteSucesso() {
        var request = new AtualizaClienteRequest("11111111111", LocalDate.now(), "11111111111", null);
        var jsonRequest = assertDoesNotThrow(() -> this.mapper.writeValueAsString(request));
        var response = new AtualizaClienteResponse("11111111111", LocalDate.now(), "11111111111", null);
        when(this.service.atualizar(anyString(), any(AtualizaClienteRequest.class))).thenReturn(response);
        var jsonResponse = assertDoesNotThrow(() -> this.mapper.writeValueAsString(response));

        assertDoesNotThrow(() ->
                this.mockMvc.perform(put(String.format("/clientes/%s", UUID.randomUUID()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().string(jsonResponse))
        );
    }

    @Test
    void atualizarClienteNaoExiste() {
        var request = new AtualizaClienteRequest("11111111111", LocalDate.now(), "11111111111", null);
        var jsonRequest = assertDoesNotThrow(() -> this.mapper.writeValueAsString(request));
        when(this.service.atualizar(anyString(), any(AtualizaClienteRequest.class))).thenThrow(new ExceptionCliente());

        assertDoesNotThrow(() ->
                this.mockMvc.perform(put(String.format("/clientes/%s", UUID.randomUUID()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andDo(print())
                        .andExpect(status().isUnprocessableEntity())
        );
    }

    @Test
    void deletarClienteSucesso() {
        doNothing().when(this.service).deletar(anyString());
        assertDoesNotThrow(() ->
                this.mockMvc.perform(delete(String.format("/clientes/%s", UUID.randomUUID())))
                        .andDo(print())
                        .andExpect(status().isOk())
        );
    }

    @Test
    void deletarClienteNaoEncontrado() {
        doThrow(new ExceptionCliente()).when(this.service).deletar(anyString());
        assertDoesNotThrow(() ->
                this.mockMvc.perform(delete(String.format("/clientes/%s", UUID.randomUUID())))
                        .andDo(print())
                        .andExpect(status().isUnprocessableEntity())
        );
    }

    @Test
    void deletarClienteRecursoNaoEncontrado() {
        doThrow(new ExceptionCliente()).when(this.service).deletar(anyString());
        assertDoesNotThrow(() ->
                this.mockMvc.perform(delete(String.format("/clientes/deletar/%s", UUID.randomUUID())))
                        .andDo(print())
                        .andExpect(status().isNotFound())
        );
    }

    @Test
    void buscarPorDataNascimento() {
        var clientes = new ArrayList<ConsultaClienteResponse>();
        clientes.add(new ConsultaClienteResponse());
        when(this.service.buscarCliente(any(ConsultaClienteRequest.class), any(Pageable.class)))
                .thenReturn(clientes);
        assertDoesNotThrow(() ->
                this.mockMvc.perform(get("/clientes")
                        .param("nascimento", LocalDate.now().format(DateTimeFormatter.ISO_DATE)))
                        .andDo(print())
                        .andExpect(status().isOk())
        );
    }

    @Test
    void buscarPorNome() {
        var clientes = new ArrayList<ConsultaClienteResponse>();
        clientes.add(new ConsultaClienteResponse());
        when(this.service.buscarCliente(any(ConsultaClienteRequest.class), any(Pageable.class)))
                .thenReturn(clientes);
        assertDoesNotThrow(() ->
                this.mockMvc.perform(get("/clientes")
                        .param("nome", UUID.randomUUID().toString()))
                        .andDo(print())
                        .andExpect(status().isOk())
        );
    }

    @Test
    void buscarPorTelefone() {
        var clientes = new ArrayList<ConsultaClienteResponse>();
        clientes.add(new ConsultaClienteResponse());
        when(this.service.buscarCliente(any(ConsultaClienteRequest.class), any(Pageable.class)))
                .thenReturn(clientes);
        assertDoesNotThrow(() ->
                this.mockMvc.perform(get("/clientes")
                        .param("telefone", UUID.randomUUID().toString()))
                        .andDo(print())
                        .andExpect(status().isOk())
        );
    }

    @Test
    void buscarPorCPF() {
        var clientes = new ArrayList<ConsultaClienteResponse>();
        clientes.add(new ConsultaClienteResponse());
        when(this.service.buscarCliente(any(ConsultaClienteRequest.class), any(Pageable.class)))
                .thenReturn(clientes);
        assertDoesNotThrow(() ->
                this.mockMvc.perform(get("/clientes")
                        .param("cpf", UUID.randomUUID().toString()))
                        .andDo(print())
                        .andExpect(status().isOk())
        );
    }

    @Test
    void buscarPorPaginacao() {
        var clientes = new ArrayList<ConsultaClienteResponse>();
        clientes.add(new ConsultaClienteResponse());
        when(this.service.buscarCliente(any(ConsultaClienteRequest.class), any(Pageable.class)))
                .thenReturn(clientes);
        assertDoesNotThrow(() ->
                this.mockMvc.perform(get("/clientes")
                        .param("pageNumber", "1")
                        .param("pageSize", "1"))
                        .andDo(print())
                        .andExpect(status().isOk())
        );
    }

    @Test
    void buscarPorTodosFiltros() {
        var clientes = new ArrayList<ConsultaClienteResponse>();
        clientes.add(new ConsultaClienteResponse());
        when(this.service.buscarCliente(any(ConsultaClienteRequest.class), any(Pageable.class)))
                .thenReturn(clientes);
        assertDoesNotThrow(() ->
                this.mockMvc.perform(get("/clientes")
                        .param("nascimento", LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                        .param("nome", UUID.randomUUID().toString())
                        .param("cpf", UUID.randomUUID().toString())
                        .param("telefone", UUID.randomUUID().toString())
                        .param("pageNumber", "1")
                        .param("pageSize", "1"))
                        .andDo(print())
                        .andExpect(status().isOk())
        );
    }

    @Test
    void buscarClienteNaoEncontrado() {
        when(this.service.buscarCliente(any(ConsultaClienteRequest.class), any(Pageable.class)))
                .thenReturn(Collections.emptyList());
        assertDoesNotThrow(() ->
                this.mockMvc.perform(get("/clientes"))
                        .andDo(print())
                        .andExpect(status().isNoContent())
        );
    }
}

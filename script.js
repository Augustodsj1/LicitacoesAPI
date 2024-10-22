const apiBaseUrl = 'http://localhost:8080/api/licitacoes'; // Altere a porta se necessário

document.getElementById('listar-licitacoes').addEventListener('click', async () => {
    const response = await fetch(apiBaseUrl);
    const data = await response.json();
    exibirResultado(data, 'licitacoes-list');
});

document.getElementById('marcar-lida').addEventListener('click', async () => {
    const id = document.getElementById('licitacao-id-lida').value;
    await fetch(`${apiBaseUrl}/${id}/marcarLida`, { method: 'POST' });
    alert(`Licitação ${id} marcada como lida!`);
});

document.getElementById('marcar-nao-lida').addEventListener('click', async () => {
    const id = document.getElementById('licitacao-id-nao-lida').value;
    await fetch(`${apiBaseUrl}/${id}/marcarNaoLida`, { method: 'POST' });
    alert(`Licitação ${id} marcada como não lida!`);
});

// Listar licitações lidas
document.getElementById('listar-licitacoes-lidas').addEventListener('click', async () => {
    const response = await fetch(`${apiBaseUrl}/lidas`);
    const data = await response.json();
    exibirResultado(data, 'licitacoes-lidas-list');
});

// Listar licitações não lidas
document.getElementById('listar-licitacoes-nao-lidas').addEventListener('click', async () => {
    const response = await fetch(`${apiBaseUrl}/naolidas`);
    const data = await response.json();
    exibirResultado(data, 'licitacoes-nao-lidas-list');
});

// Ver detalhes da licitação
document.getElementById('ver-detalhes').addEventListener('click', async () => {
    const id = document.getElementById('licitacao-id-detalhes').value;
    const response = await fetch(`${apiBaseUrl}/${id}`);
    const data = await response.json();
    mostrarDetalhes(data);
});

function exibirResultado(data, listId) {
    const lista = document.getElementById(listId);
    lista.innerHTML = '';

    if (data.message) {
        const mensagem = document.createElement('li');
        mensagem.textContent = data.message;
        lista.appendChild(mensagem);
        return;
    }

    data.forEach(licitacao => {
        const item = document.createElement('li');
        item.textContent = `Número: ${licitacao.numero}, Descrição: ${licitacao.descricao}, Lida: ${licitacao.lida}`;
        lista.appendChild(item);
    });
}

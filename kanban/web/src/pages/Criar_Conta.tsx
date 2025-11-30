import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logo from '../assets/logo_copyon.png';
import './Login.css';

const API_BASE_URL = 'http://localhost:8080/api';

export function Criar_Conta() {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [confirmSenha, setConfirmSenha] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (senha !== confirmSenha) {
      setError('As senhas não coincidem');
      return;
    }

    try {
      const response = await fetch(`${API_BASE_URL}/usuario`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          nome,
          email,
          senha
        })
      });

      if (!response.ok) {
        const data = await response.json();
        throw new Error(data.message || 'Erro ao criar conta');
      }

      setSuccess('Conta criada com sucesso! Redirecionando para login...');
      setTimeout(() => navigate('/'), 1500); // redireciona após 1.5s

    } catch (err: any) {
      setError(err.message);
    }
  };

  return (
    <div className="d-flex align-items-center py-4 bg-body-tertiary h-100 login-wrapper">
      <main className="w-100 m-auto form-container">
        <form onSubmit={handleSubmit}>
          <img 
            src={logo} 
            className="mb-4" 
            height="70" 
            width="70" 
            alt="Logo CopyOn"
          />
          
          <h1 className="h3 mb-3 fw-normal">Crie sua Conta</h1>

          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          <div className="form-floating mb-3">
            <input 
              type="text"
              className="form-control" 
              id="floatingName" 
              placeholder="Seu Nome Completo" 
              value={nome}
              onChange={e => setNome(e.target.value)}
              required
            />
            <label htmlFor="floatingName">Nome Completo</label>
          </div>

          <div className="form-floating mb-3"> 
            <input 
              type="email" 
              className="form-control" 
              id="floatingEmail" 
              placeholder="usuario@gmail.com" 
              value={email}
              onChange={e => setEmail(e.target.value)}
              required 
            />
            <label htmlFor="floatingEmail">E-mail</label>
          </div>

          <div className="form-floating mb-3">
            <input 
              type="password" 
              className="form-control" 
              id="floatingPassword" 
              placeholder="Senha" 
              value={senha}
              onChange={e => setSenha(e.target.value)}
              required 
            />
            <label htmlFor="floatingPassword">Senha</label>
          </div>

          <div className="form-floating mb-3">
            <input 
              type="password" 
              className="form-control" 
              id="floatingConfirm" 
              placeholder="Confirme a Senha" 
              value={confirmSenha}
              onChange={e => setConfirmSenha(e.target.value)}
              required 
            />
            <label htmlFor="floatingConfirm">Confirme a Senha</label>
          </div>

          <button className="btn btn-primary w-100 py-2 my-3" type="submit">
            Criar Conta
          </button>
          
          <Link to="/">Entrar</Link>
          
          <p className="text-body-secondary mt-5 mb-3">© 2024-2025</p>
        </form>
      </main>
    </div>
  );
}

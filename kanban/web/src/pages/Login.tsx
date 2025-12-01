import './Login.css';
import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import logo from '../assets/logo_copyon.png';

const API_BASE_URL = 'http://localhost:8080/api';

export function Login() {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, senha })
      });

      if (!response.ok) throw new Error('Usuário ou senha inválidos');

      const data = await response.json();
      localStorage.setItem('token', data.token);
      localStorage.setItem('email', email);

      navigate('/home'); // redireciona para home
    } catch (err: any) {
      setError(err.message);
    }
  };

  return (
    <div className="login-wrapper bg-body-tertiary">
      <main className="w-100 m-auto form-container">
        <form onSubmit={handleLogin}>
          <img src={logo} className="mb-4" height="70" width="70" alt="Logo CopyOn" />
          <h1 className="h3 mb-3 fw-normal">Entre</h1>

          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              id="floatingInput"
              placeholder="usuario@gmail.com"
              value={email}
              onChange={e => setEmail(e.target.value)}
              required
            />
            <label htmlFor="floatingInput">E-mail</label>
          </div>

          <div className="form-floating mb-3">
            <input
              type="password"
              className="form-control"
              id="floatingPassword"
              placeholder="senha"
              value={senha}
              onChange={e => setSenha(e.target.value)}
              required
            />
            <label htmlFor="floatingPassword">Senha</label>
          </div>

          {error && <div className="alert alert-danger">{error}</div>}

          <button className="btn btn-primary w-100 py-2 my-3" type="submit">
            Entrar
          </button>

          <div className="text-center">
            {/* Corrigido para usar o path correto da rota */}
            <Link to="/criar-conta">Criar Conta</Link>
          </div>
        </form>
      </main>
    </div>
  );
}

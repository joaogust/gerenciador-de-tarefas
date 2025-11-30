import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Login } from "./pages/Login";
import { Criar_Conta } from "./pages/Criar_Conta";
import { Home } from "./pages/Home";
import KanbanBoard from "./pages/KanbanBoard";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/criar-conta" element={<Criar_Conta />} />
                <Route path="/home" element={<Home />} />
                
                {/* Página do Kanban */}
                <Route path="/board/:id" element={<KanbanBoard />} />

            </Routes>
        </Router>
    );
}

export default App;

import React, { useState } from "react";
import Card from "../../components/common/PanelModerador/Card";
import InputField from "../../components/ui/PanelModerador/InputField";
import ForceGraph2D from "react-force-graph-2d";

type Estudiante = {
  id: string;
  nombre: string;
};

type Arista = {
  source: string;
  target: string;
  peso: number;
};

const ShortestPath: React.FC = () => {
  const [sourceUser, setSourceUser] = useState("");
  const [targetUser, setTargetUser] = useState("");
  const [nodos, setNodos] = useState<Estudiante[]>([]);
  const [aristas, setAristas] = useState<Arista[]>([]);
  const [showResult, setShowResult] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    fetch(`http://localhost:8080/api/camino?origen=${sourceUser}&destino=${targetUser}`) // Ajusta el endpoint
      .then((res) => res.json())
      .then((data) => {
        setNodos(data.estudiantes);
        setAristas(data.camino);
        setShowResult(true);
      })
      .catch((err) => {
        console.error("Error al obtener el camino más corto:", err);
        setShowResult(false);
      });
  };

  return (
    <Card title="Ver camino mas corto entre 2 estudiantes" className="h-[330px] flex flex-col">
      <div className="flex flex-row">
        <div className="w-1/2 pr-4">
          <form onSubmit={handleSubmit} className="bg-white shadow-[0px_4px_4px_#0c0c0d0c] border border-[#d9d9d9] rounded-lg p-4">
            <div className="mb-6">
              <InputField
                placeholder="Nombre de usuario"
                value={sourceUser}
                onChange={(e) => setSourceUser(e.target.value)}
                className="h-10 text-lg font-medium text-[#828282]"
              />
            </div>
            <div>
              <InputField
                placeholder="Nombre de usuario"
                value={targetUser}
                onChange={(e) => setTargetUser(e.target.value)}
                className="h-10 text-lg font-medium text-[#828282]"
              />
            </div>
            <button type="submit" className="mt-4 bg-primary text-white px-4 py-2 rounded-lg">
              Generar
            </button>
          </form>
        </div>
        <div className="w-1/2 pl-4 flex justify-center">
          {showResult ? (
            <div className="w-[282px] h-[282px]">
              <ForceGraph2D
                graphData={{
                  nodes: nodos,
                  links: aristas
                }}
                nodeLabel="nombre"
                nodeAutoColorBy="id"
                width={282}
                height={282}
                linkDirectionalParticles={2}
              />
            </div>
          ) : (
            <div className="flex items-center justify-center h-full text-gray-500 text-center px-4">
              Ingresa dos usuarios para ver el camino más corto
            </div>
          )}
        </div>
      </div>
    </Card>
  );
};

export default ShortestPath;
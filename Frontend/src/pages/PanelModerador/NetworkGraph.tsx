import React, { useEffect, useState } from "react";
import ForceGraph2D from "react-force-graph-2d";
import Card from "../../components/common/PanelModerador/Card";

type Estudiante = {
  id: string;
  nombre: string;
};

type Afinidad = {
  source: string;
  target: string;
  peso: number;
};

const NetworkGraph: React.FC = () => {
  const [nodos, setNodos] = useState<Estudiante[]>([]);
  const [aristas, setAristas] = useState<Afinidad[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/grafo") // Ajusta la URL si es necesario
      .then((res) => res.json())
      .then((data) => {
        const estudiantes: Estudiante[] = data.estudiantes;
        const matriz: number[][] = data.matrizAdyacencia;
        const links = generarLinksDesdeMatriz(matriz, estudiantes);

        setNodos(estudiantes);
        setAristas(links);
      })
      .catch((err) => {
        console.error("Error al cargar el grafo:", err);
      });
  }, []);

  const generarLinksDesdeMatriz = (matriz: number[][], estudiantes: Estudiante[]): Afinidad[] => {
    const links: Afinidad[] = [];
    for (let i = 0; i < matriz.length; i++) {
      for (let j = i + 1; j < matriz[i].length; j++) {
        const peso = matriz[i][j];
        if (peso > 0) {
          links.push({
            source: estudiantes[i].id,
            target: estudiantes[j].id,
            peso,
          });
        }
      }
    }
    return links;
  };

  return (
    <Card title="Grafo de afinidad" className="h-[733px]">
      <div className="flex justify-center mt-4">
        <div className="w-[353px] h-[353px]">
          <ForceGraph2D
            graphData={{ nodes: nodos, links: aristas }}
            nodeLabel="nombre"
            nodeAutoColorBy="id"
            width={353}
            height={353}
            linkDirectionalParticles={2}
            linkDirectionalParticleSpeed={(d) => (d as any).peso * 0.001}
          />
        </div>
      </div>
    </Card>
  );
}; que concha e esto

export default NetworkGraph;

commit_name="Product repository, service, controller, GET ok"

echo "Inserindo modificações no github"
echo "Nome do commit: ${commit_name}"
read -p "Deseja continuar? [n/S]: "  answer
if [ "$answer" = "S" ] || [ "$answer" = "s" ]; then
    git add .
    git commit -m "${commit_name}"
    git push
else
    echo "Operação cancelada"
fi  
echo "Fim da execução"

read -p "Pressione alguma tecla para encerrar...."  answer
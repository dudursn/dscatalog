
echo "Inserindo modificações no github"
read -p "Informe o nome do commit: " commit_name
read -p "COMMIT NAME: '${commit_name}'. Deseja continuar? [n/S]: "  answer
if [ "$answer" = "S" ] || [ "$answer" = "s" ]; then
    git add .
    git commit -m "${commit_name}"
    git push
else
    echo "Operação cancelada"
fi  
echo "Fim da execução"

read -p "Pressione alguma tecla para encerrar...."  answer
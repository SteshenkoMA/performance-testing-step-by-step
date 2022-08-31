#Ansible

These playbooks will deploy and setup Performance Testing Infrastructure

1. Choose cloud, export variables, order virtual machines:
 ```
cd ./performance-testing-step-by-step/ansible/cloud/yandex
export YANDEX_OAUTH_TOKEN='EXAMPLE_123'
ansible-playbook yandex-playbook.yml -vv

or

cd ./performance-testing-step-by-step/ansible/cloud/aws
export AWS_ACCESS_KEY_ID='EXAMPLE_456'
export AWS_SECRET_ACCESS_KEY='EXAMPLE_678
ansible-playbook aws-playbook.yml -vv
```

2. Setup and configure InfluxDB
```
cd ./performance-testing-step-by-step/ansible/influxdb
ansible-playbook influxdb-playbook.yml -i ../hosts.ini -vv --ssh-common-args='-o StrictHostKeyChecking=no'
```

3. Setup and configure Telegraf agents
```
cd ./performance-testing-step-by-step/ansible/telegraf
ansible-playbook telegraf-playbook.yml -i ../hosts.ini -vv --ssh-common-args='-o StrictHostKeyChecking=no'
```

3. Setup and configure Grafana
```
cd ./performance-testing-step-by-step/ansible/grafana
ansible-playbook grafana-playbook.yml -i ../hosts.ini -vv --ssh-common-args='-o StrictHostKeyChecking=no'
```

4. Build and deploy Eshop
```
cd ./performance-testing-step-by-step/ansible/eshop
ansible-playbook eshop-playbook.yml -i ../hosts.ini -vv --ssh-common-args='-o StrictHostKeyChecking=no'
```

5. Build and deploy Load generator
```
cd ./performance-testing-step-by-step/ansible/load_generator
ansible-playbook load_generator-playbook.yml -i ../hosts.ini -vv --ssh-common-args='-o StrictHostKeyChecking=no'
```

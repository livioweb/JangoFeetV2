# -*- mode: ruby -*-
# vi: set ft=ruby :
N = 3
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  config.ssh.insert_key = true

  config.vm.define "r2d2" do |r2d2|
    r2d2.vm.box = "ubuntu/bionic64"
    r2d2.vm.network "private_network", ip: "172.17.177.100"
    #r2d2.vm.network "private_network", type: "dhcp"
    r2d2.vm.network "forwarded_port", guest: 54321, host: 54321, autocorrect: true
    r2d2.vm.hostname = "r2d2"
    r2d2.vm.synced_folder "r2d2/", "/home/vagrant/r2d2"
    r2d2.vm.provision "shell", path: "r2d2/script.sh"
        r2d2.vm.provider :virtualbox do |r2d2|
            r2d2.customize ["modifyvm", :id, "--memory", "2048"]
            r2d2.customize ["modifyvm", :id, "--cpus", "2"]
        end
        r2d2.vm.provision "ansible" do |r2d2|
            r2d2.verbose = "v"
            r2d2.compatibility_mode = "2.0"
            r2d2.playbook = "r2d2/provisioning/playbook.yml"
            r2d2.inventory_path = "r2d2/provisioning/inventory"
            r2d2.become = true
            r2d2.limit = "r2d2"
            r2d2.extra_vars = {
                node_ip: "172.17.177.100",
            }
        end
  end


    config.vm.define :bb8 do |bb8|

        bb8.vm.provider :virtualbox do |v|
            v.name = "bb8"
            v.customize [
                "modifyvm", :id,
                "--name", "bb8",
                "--memory", 512,
                "--natdnshostresolver1", "on",
                "--cpus", 1,
            ]
        end

        bb8.vm.box = "ubuntu/bionic64"
        bb8.vm.network :private_network, ip: "172.17.177.200"
        bb8.ssh.forward_agent = true
        bb8.vm.synced_folder "./", "/vagrant", :nfs => true
        bb8.vm.hostname = "bb8"

        bb8.vm.provision "ansible" do |bb8|
            bb8.verbose = "v"
            bb8.compatibility_mode = "2.0"
            bb8.playbook = "bb8/provisioning/playbook.yml"
            bb8.inventory_path = "bb8/provisioning/inventory"
            bb8.become = true
            bb8.limit = "bb8"
            bb8.extra_vars = {
                node_ip: "172.17.177.200",
            }
        end

    end

    (1..3).each do |i|
        config.vm.define "xwing_#{i}" do |xwings|
            xwings.vm.provider :virtualbox do |v|
                v.name = "xwing_#{i}"
                v.customize [
                    "modifyvm", :id,
                    "--name", "xwing-#{i}",
                    "--memory", 512,
                    "--natdnshostresolver1", "on",
                    "--cpus", 1,
                ]
            end
            xwings.vm.box = "ubuntu/bionic64"
            xwings.vm.network :private_network, ip: "172.17.177.20#{i}"
            xwings.ssh.forward_agent = true
            xwings.vm.synced_folder "./", "/vagrant", :nfs => true, :mount_options => ['vers=3','noatime','actimeo=2', 'tcp', 'fsc']
            xwings.vm.hostname = "xwing-#{i}"

            xwings.vm.provision "ansible" do |xwings|
                xwings.verbose = "v"
                xwings.compatibility_mode = "2.0"
                xwings.playbook = "xwings/provisioning/playbook.yml"
                xwings.inventory_path = "xwings/provisioning/inventory"
                xwings.become = true
                xwings.limit = "xwings"
                xwings.extra_vars = {
                    node_ip: "172.17.177.20#{i}",
                }
            end

        xwings.vm.provision "shell", inline: <<-SHELL
          echo "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDoXmBHMI+z1YOx+B/tSWQu8yF5WgIRjdxvTkEOx+I+U6MCL6YnvyJgMAG4gSAh8I9/pAiOpn/JpmXYgwhgWBV6yr8zon4hd5qJM9XJK6MlKJwDD4ag6Qtg/orG3gflf7KObINpjrwyq+LphJf/evs7z34JQoX1qzqg3SkFtesjs9s/qMykwpTaDe4Fr31FMCdyuZEebL51fGBjUZT9XmW0hBKPedaQdGWWpYYRQ1wSIBEt20aWhu1JfdKrr498wjbdLcCOqQ4cS07UcroH7wRPQp/NjYKMD3xYvPFvCUg/BuGK8cenH+aH3Uv9AjYS5CyAYYbNbRN+SzdOaQohUzQv anakin@r2d2" >> /home/vagrant/.ssh/authorized_keys
          echo "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCv/qDz53vlV7ix5npgjcDr+VZNTlGuhnQzEZxfL833fMq3Jq2MhFk5lAsG6wVtkLlfO899aF8jinXsYPPqqjSE/f7vrSiBE+1UwgvAWuzie8GmT3/4Zbbxl8VGOU7T3vUDxl1NCUOxXx+ewSMwx8uUpfcUwJ1A1FQKnIcoeDZiOjo+lRWKWankN/MlCzU+YdZVLxWcw8qLFSeNqaj+WT0bZpI2lYyJLQiamjL6Izh56zsY1njqc4QZxXiUiLpeMc3kw1mcs6d0KpTLxTaYP/fpfI4nMccNTkHudKA/aXQDD8+iwIs7WEnZY8OybP9g2stUH/L7i3CYotKKbHmUNihD jenkins@jenkins.docker" >> /home/vagrant/.ssh/authorized_keys
        SHELL


        end
    end


  config.vm.define "c3po" do |c3po|
    c3po.vm.box = "ubuntu/bionic64"
    c3po.vm.network "private_network", ip: "172.17.177.110"
    c3po.vm.network "forwarded_port", host: 8080, guest: 80

    c3po.vm.hostname = "c3po"

        c3po.vm.provider :virtualbox do |c3po|
            c3po.customize ["modifyvm", :id, "--memory", "512"]
            c3po.customize ["modifyvm", :id, "--cpus", "1"]
            c3po.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
        end

    c3po.vm.synced_folder "c3po/", "/home/vagrant/c3po"
    #c3po.vm.synced_folder "/etc/hosts/", "/etc/hosts"
    c3po.vm.provision "shell", path: "c3po/script.sh"
        c3po.vm.provision "ansible" do |c3po|
            c3po.verbose = "v"
            c3po.compatibility_mode = "2.0"
            c3po.playbook = "c3po/provisioning/playbook.yml"
            c3po.inventory_path = "c3po/provisioning/inventory"
            c3po.become = true
            c3po.limit = "c3po"
         end
  end

  config.vm.define "gitlab" do |gitlab|
    gitlab.vm.box = "ubuntu/bionic64"
    gitlab.vm.network "private_network", ip: "172.17.177.120"

    gitlab.vm.hostname = "gitlab"
    gitlab.vm.synced_folder "gitlab/", "/home/vagrant/gitlab"
    gitlab.vm.provision "shell", path: "gitlab/script.sh"
        gitlab.vm.provider :virtualbox do |gitlab|
            gitlab.customize ["modifyvm", :id, "--memory", "8096"]
            gitlab.customize ["modifyvm", :id, "--cpus", "4"]
        end

        gitlab.vm.provision "ansible" do |gitlab|
            gitlab.verbose = "v"
            gitlab.compatibility_mode = "2.0"
            gitlab.playbook = "gitlab/provisioning/playbook.yml"
            gitlab.inventory_path = "gitlab/provisioning/inventory"
            gitlab.become = true
            gitlab.limit = "gitlab"
         end
  end



  config.vm.define "jenkins" do |jenkins|
    jenkins.vm.box = "ubuntu/bionic64"
    jenkins.vm.network "private_network", ip: "172.17.177.130"

    jenkins.vm.hostname = "jenkins"
    jenkins.vm.synced_folder "jenkins/", "/home/vagrant/jenkins"
    jenkins.vm.provision "shell", path: "jenkins/script.sh"

        jenkins.vm.provider :virtualbox do |jenkins|
            jenkins.customize ["modifyvm", :id, "--memory", "4096"]
            jenkins.customize ["modifyvm", :id, "--cpus", "4"]
        end

        jenkins.vm.provision "ansible" do |jenkins|
            jenkins.verbose = "vv"
            jenkins.compatibility_mode = "2.0"
            jenkins.playbook = "jenkins/provisioning/playbook.yml"
            jenkins.inventory_path = "jenkins/provisioning/inventory"
            jenkins.become = true
            jenkins.limit = "jenkins"
         end
  end


  config.vm.define "sonar" do |sonar|
    sonar.vm.box = "ubuntu/bionic64"
    sonar.vm.network "private_network", ip: "172.17.177.140"

    sonar.vm.hostname = "sonar"
    sonar.vm.synced_folder "sonar/", "/home/vagrant/sonar"
    sonar.vm.provision "shell", path: "sonar/script.sh"

        sonar.vm.provider :virtualbox do |sonar|
            sonar.customize ["modifyvm", :id, "--memory", "2048"]
            sonar.customize ["modifyvm", :id, "--cpus", "2"]
        end

        sonar.vm.provision "ansible" do |sonar|
            sonar.verbose = "vv"
            sonar.compatibility_mode = "2.0"
            sonar.playbook = "sonar/provisioning/playbook.yml"
            sonar.inventory_path = "sonar/provisioning/inventory"
            sonar.become = true
            sonar.limit = "sonar"
         end
  end

end


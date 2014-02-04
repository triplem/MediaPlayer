import os

from fabric.state import env
from fabric.api import run
from fabric.context_managers import cd
from fabric.utils import puts, abort
from fabric.operations import put
from fabric.contrib.files import exists

def deploy_core():
    puts('Deploying core')

    user = env.user

    if (user != 'root'):
        home_dir = '/home/%s' % user
    else:
        home_dir = '/root'

    if not exists(home_dir):
        abort('No home directory, cannot deploy application')

    app_dir = os.path.join(home_dir, 'mediaplayer')

    if (exists(app_dir)):
        run('rm %s -rf' % app_dir)

    run('mkdir -p %s' % app_dir)

    put('core/target/mediaplayer-*-bin.zip', app_dir)

    with cd(app_dir):
        run('unzip mediaplayer-*-bin.zip')

def deploy_plugin(plugin_name):
    puts('Deploying Plugin %s' % plugin_name)

    user = env.user

    plugin = 'plugin-%s' % plugin_name

    plugin_dir = plugin.lower()

    if not exists(plugin_dir):
        abort('No plugin directory found, cannot deploy it')

    full_plugin_path = os.path.join(plugin_dir, 'target')
    plugin_file_name = plugin_name + "-plugin.zip"
    plugin_file = os.path.join(full_plugin_path, plugin_file_name)

    if (user != 'root'):
        home_dir = '/home/%s' % user
    else:
        home_dir = '/root'

    app_dir = os.path.join(home_dir, 'mediaplayer')

    put(plugin_file, app_dir)

    with cd(app_dir):
        run('unzip %s' % plugin_file_name)

def deploy_lastfm():
    deploy_core()
    deploy_plugin('LastFM')